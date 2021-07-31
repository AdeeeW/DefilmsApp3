package com.adewijayanto.defilmsapp3.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.vo.Resources
import com.bumptech.glide.load.engine.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resources<PagedList<MovieCatalogueEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MovieCatalogueEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieTvRepository)
    }

    @Test
    fun getMovie() {
        val dummyMovies = Resources.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(10)
        val movies = MutableLiveData<Resources<PagedList<MovieCatalogueEntity>>>()
        movies.value = dummyMovies

        `when`(movieTvRepository.loadMovieApi("Newest")).thenReturn(movies)
        val moviesEntities = viewModel.getMovie("Newest").value?.data
        verify(movieTvRepository).loadMovieApi("Newest")
        assertNotNull(moviesEntities)
        assertEquals(10, moviesEntities?.size)

        viewModel.getMovie("Newest").observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}