package com.adewijayanto.defilmsapp3.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.data.local.database.MovieTvDao
import com.adewijayanto.defilmsapp3.data.remote.RemoteDataSource
import com.adewijayanto.defilmsapp3.utils.SortUtils

class LocalDataSource(val movieTvDao: MovieTvDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(filmDao: MovieTvDao): LocalDataSource =
                instance ?: LocalDataSource(filmDao)
    }

    fun getDataMovie(sort: String): DataSource.Factory<Int, MovieCatalogueEntity> {
        val query = SortUtils.getSortedMovieQuery(sort)
        return movieTvDao.getMovies(query)
    }

    fun getMovieById(id: Int): LiveData<MovieCatalogueEntity> = movieTvDao.getMovieById(id)

    fun getDataTV(sort: String): DataSource.Factory<Int, TvShowCatalogueEntity> {
        val query = SortUtils.getSortedTvShowQuery(sort)
        return movieTvDao.getTvShows(query)
    }

    fun getTvShowById(id: Int): LiveData<TvShowCatalogueEntity> = movieTvDao.getTvShowById(id)

    fun insertMovieTv(movieCatalogueTvItems: ArrayList<MovieCatalogueEntity>) =
            movieTvDao.insertMovies(movieCatalogueTvItems)

    fun insertTvShow(movieCatalogueTvItems: ArrayList<TvShowCatalogueEntity>) =
            movieTvDao.insertTvShows(movieCatalogueTvItems)

    fun updateMovie(movie: MovieCatalogueEntity, newState: Boolean) {
        movie.favorite = newState
        movieTvDao.updateMovie(movie)
    }

    fun updateTvShow(tvshow: TvShowCatalogueEntity, newState: Boolean) {
        tvshow.favorite = newState
        movieTvDao.updateTvShow(tvshow)
    }

    fun setFavoriteMovie(movie: MovieCatalogueEntity, newState: Boolean) {
        movie.favorite = newState
        movieTvDao.updateMovie(movie)
    }

    fun setFavoriteTvShow(tvShow: TvShowCatalogueEntity, newState: Boolean) {
        tvShow.favorite = newState
        movieTvDao.updateTvShow(tvShow)
    }

    fun getFavoriteMovie(): DataSource.Factory<Int, MovieCatalogueEntity> = movieTvDao.getFavMovies()

    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowCatalogueEntity> = movieTvDao.getFavTvShows()
}