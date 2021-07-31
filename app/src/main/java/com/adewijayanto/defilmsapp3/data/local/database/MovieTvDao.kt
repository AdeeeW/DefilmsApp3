package com.adewijayanto.defilmsapp3.data.local.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity

@Dao
interface MovieTvDao {
    @RawQuery(observedEntities = [MovieCatalogueEntity::class])
    fun getMovies(query: SimpleSQLiteQuery): DataSource.Factory<Int, MovieCatalogueEntity>

    @Query("SELECT * FROM movie_entity WHERE id = :id")
    fun getMovieById(id: Int): LiveData<MovieCatalogueEntity>

    @Query("SELECT * FROM movie_entity WHERE favorite = 1")
    fun getFavMovies(): DataSource.Factory<Int, MovieCatalogueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieCatalogueEntity>)

    @Update
    fun updateMovie(movie: MovieCatalogueEntity)

    @RawQuery(observedEntities = [TvShowCatalogueEntity::class])
    fun getTvShows(query: SimpleSQLiteQuery): DataSource.Factory<Int, TvShowCatalogueEntity>

    @Query("SELECT * FROM tv_show_entity WHERE id = :id")
    fun getTvShowById(id: Int): LiveData<TvShowCatalogueEntity>

    @Query("SELECT * FROM tv_show_entity WHERE favorite = 1")
    fun getFavTvShows(): DataSource.Factory<Int, TvShowCatalogueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowCatalogueEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowCatalogueEntity)
}