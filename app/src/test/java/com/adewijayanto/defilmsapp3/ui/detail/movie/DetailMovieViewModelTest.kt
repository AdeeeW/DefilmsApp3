package com.adewijayanto.defilmsapp3.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.ui.detail.tvshow.DetailTvShowViewModel
import com.adewijayanto.defilmsapp3.utils.DataDummy
import com.adewijayanto.defilmsapp3.vo.Resources
import com.bumptech.glide.load.engine.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {
    private lateinit var viewModel: DetailMovieViewModel

    private val dummyMovie = DataDummy.generateDummyDetailMovie()
    private val dummyMovieId = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var movieObserver: Observer<Resources<MovieCatalogueEntity>>

    @Before
    fun setUpMovie() {
        viewModel = DetailMovieViewModel(movieTvRepository)
        viewModel.setSelectedMovie(dummyMovieId)
    }

    @Test
    fun getMovie() {
        val dummyMovieDetail = Resources.success(DataDummy.generateDummyDetailMovie())
        val movie = MutableLiveData<Resources<MovieCatalogueEntity>>()
        movie.value = dummyMovieDetail

        Mockito.`when`(movieTvRepository.loadDetailMovieApi(dummyMovieId)).thenReturn(movie)
        viewModel.movie.observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovieDetail)
    }

    @Test
    fun setFavoriteMovie() {
        val dummyMovieFavorite = Resources.success(DataDummy.generateDummyDetailMovie())
        val movie = MutableLiveData<Resources<MovieCatalogueEntity>>()
        val newState = !dummyMovie.favorite
        movie.value = dummyMovieFavorite
        Mockito.`when`(movieTvRepository.loadDetailMovieApi(dummyMovieId)).thenReturn(movie)

        doNothing().`when`(movieTvRepository).setFavoriteMovie(dummyMovie, newState)
        viewModel.movie.observeForever(movieObserver)
        viewModel.setFavoriteMovie()
        verify(movieTvRepository, Mockito.times(1)).setFavoriteMovie(dummyMovie, newState)
    }
}