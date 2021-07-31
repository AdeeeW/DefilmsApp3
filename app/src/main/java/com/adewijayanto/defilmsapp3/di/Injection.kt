package com.adewijayanto.defilmsapp3.di

import android.content.Context
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.local.LocalDataSource
import com.adewijayanto.defilmsapp3.data.local.database.MovieTvDatabase
import com.adewijayanto.defilmsapp3.data.remote.RemoteDataSource
import com.adewijayanto.defilmsapp3.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieTvRepository {
        val database = MovieTvDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()
        return MovieTvRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}