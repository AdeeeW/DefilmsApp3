package com.adewijayanto.defilmsapp3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.vo.Resources

class MovieViewModel(private val movieTvRepository: MovieTvRepository): ViewModel() {
    fun getMovie(sort: String): LiveData<Resources<PagedList<MovieCatalogueEntity>>> =
            movieTvRepository.loadMovieApi(sort)
}