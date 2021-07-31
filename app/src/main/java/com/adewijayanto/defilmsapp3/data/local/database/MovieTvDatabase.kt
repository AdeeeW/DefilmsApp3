package com.adewijayanto.defilmsapp3.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity

@Database(
        entities = [MovieCatalogueEntity::class, TvShowCatalogueEntity::class],
        version = 1,
        exportSchema = false
)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao(): MovieTvDao

    companion object {
        @Volatile
        private var INSTANCE: MovieTvDatabase? = null

        fun getInstance(context: Context): MovieTvDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: Room.databaseBuilder(
                            context.applicationContext,
                            MovieTvDatabase::class.java,
                            "CatalogueMovieTv.db"
                    ).build()
                }
    }
}