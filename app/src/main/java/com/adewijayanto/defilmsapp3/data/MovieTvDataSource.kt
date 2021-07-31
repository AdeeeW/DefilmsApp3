package com.adewijayanto.defilmsapp3.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.vo.Resources

interface MovieTvDataSource {

    fun loadMovieApi(sort: String): LiveData<Resources<PagedList<MovieCatalogueEntity>>>

    fun loadTvShowApi(sort: String): LiveData<Resources<PagedList<TvShowCatalogueEntity>>>

    fun loadDetailMovieApi(movieId: String): LiveData<Resources<MovieCatalogueEntity>>

    fun loadDetailTvShowApi(tvShowId: String): LiveData<Resources<TvShowCatalogueEntity>>

    fun getFavoriteMovie(): LiveData<PagedList<MovieCatalogueEntity>>

    fun getFavoriteTvShow(): LiveData<PagedList<TvShowCatalogueEntity>>

    fun setFavoriteMovie(movie: MovieCatalogueEntity, state: Boolean)

    fun setFavoriteTvShow(tvShow: TvShowCatalogueEntity, state: Boolean)

}