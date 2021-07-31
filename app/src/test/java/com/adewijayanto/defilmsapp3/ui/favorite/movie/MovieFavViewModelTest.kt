package com.adewijayanto.defilmsapp3.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.ui.favorite.tvshow.TvShowFavViewModel
import com.adewijayanto.defilmsapp3.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieFavViewModelTest {
    private lateinit var viewModel: MovieFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieCatalogueEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieCatalogueEntity>

    @Before
    fun setUp() {
        viewModel = MovieFavViewModel(movieTvRepository)
    }
    @Test
    fun getFavMovies() {
        val dummyMovie = pagedList
        Mockito.`when`(dummyMovie.size).thenReturn(5)
        val movies = MutableLiveData<PagedList<MovieCatalogueEntity>>()
        movies.value = dummyMovie

        Mockito.`when`(movieTvRepository.getFavoriteMovie()).thenReturn(movies)
        val movie = viewModel.getFavMovies().value
        verify(movieTvRepository).getFavoriteMovie()
        assertNotNull(movie)
        assertEquals(5, movie?.size)

        viewModel.getFavMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun setFavMovie() {
        viewModel.setFavMovie(DataDummy.generateDummyDetailMovie())
        verify(movieTvRepository).setFavoriteMovie(DataDummy.generateDummyDetailMovie(), true)
        verifyNoMoreInteractions(movieTvRepository)
    }
}