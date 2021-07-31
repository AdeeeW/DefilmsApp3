package com.adewijayanto.defilmsapp3.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.vo.Resources

class TvShowViewModel(private val movieTvRepository: MovieTvRepository): ViewModel() {
    fun getTvShow(sort: String): LiveData<Resources<PagedList<TvShowCatalogueEntity>>> =
            movieTvRepository.loadTvShowApi(sort)
}