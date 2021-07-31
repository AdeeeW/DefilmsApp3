package com.adewijayanto.defilmsapp3.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.di.Injection
import com.adewijayanto.defilmsapp3.ui.detail.movie.DetailMovieViewModel
import com.adewijayanto.defilmsapp3.ui.detail.tvshow.DetailTvShowViewModel
//import com.adewijayanto.defilmsapp3.ui.detail.movie.DetailMovieViewModel
import com.adewijayanto.defilmsapp3.ui.favorite.movie.MovieFavViewModel
import com.adewijayanto.defilmsapp3.ui.favorite.tvshow.TvShowFavViewModel
import com.adewijayanto.defilmsapp3.ui.movie.MovieViewModel
import com.adewijayanto.defilmsapp3.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val movieCatalogueRepository: MovieTvRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> {
                DetailTvShowViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavViewModel::class.java) -> {
                MovieFavViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(TvShowFavViewModel::class.java) -> {
                TvShowFavViewModel(movieCatalogueRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}