package com.adewijayanto.defilmsapp3.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.vo.Resources

class DetailMovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    private val idMovie = MutableLiveData<String>()
    var movie: LiveData<Resources<MovieCatalogueEntity>> =
        Transformations.switchMap(idMovie) { mMovieId ->
            movieTvRepository.loadDetailMovieApi(mMovieId)
        }

    fun setFavoriteMovie() {
        val resource = movie.value
        if (resource != null) {
            val movieWithData = resource.data

            if (movieWithData != null) {
                val newState = !movieWithData.favorite
                movieTvRepository.setFavoriteMovie(movieWithData, newState)
            }
        }
    }

    fun setSelectedMovie(movieId: String) {
        this.idMovie.value = movieId
    }
}