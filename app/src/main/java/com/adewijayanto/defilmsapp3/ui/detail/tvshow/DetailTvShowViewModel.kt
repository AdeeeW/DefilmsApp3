package com.adewijayanto.defilmsapp3.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.vo.Resources

class DetailTvShowViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    private val idTvShow = MutableLiveData<String>()
    var tvshow: LiveData<Resources<TvShowCatalogueEntity>> = Transformations.switchMap(idTvShow) { mTvShowId ->
        movieTvRepository.loadDetailTvShowApi(mTvShowId)
    }
    fun setFavoriteTvShow() {
        val resource = tvshow.value
        if (resource != null) {
            val tvshowWithData = resource.data

            if (tvshowWithData != null) {
                val newState = !tvshowWithData.favorite
                movieTvRepository.setFavoriteTvShow(tvshowWithData, newState)
            }
        }
    }
    fun setSelectedTvShow(movieId: String) {
        this.idTvShow.value = movieId
    }
}