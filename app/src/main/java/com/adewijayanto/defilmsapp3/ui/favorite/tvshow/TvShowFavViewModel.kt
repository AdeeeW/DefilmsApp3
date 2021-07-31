package com.adewijayanto.defilmsapp3.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity

class TvShowFavViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getFavTvShows() = movieTvRepository.getFavoriteTvShow()

    fun setFavTvShow(tvShowEntity: TvShowCatalogueEntity) {
        val newState = !tvShowEntity.favorite
        movieTvRepository.setFavoriteTvShow(tvShowEntity, newState)
    }
}