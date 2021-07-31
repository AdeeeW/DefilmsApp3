package com.adewijayanto.defilmsapp3.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.ui.movie.MovieViewModel
import com.adewijayanto.defilmsapp3.vo.Resources
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resources<PagedList<TvShowCatalogueEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowCatalogueEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieTvRepository)
    }

    @Test
    fun getMovie() {
        val dummyTvShow = Resources.success(pagedList)
        Mockito.`when`(dummyTvShow.data?.size).thenReturn(10)
        val tvshow = MutableLiveData<Resources<PagedList<TvShowCatalogueEntity>>>()
        tvshow.value = dummyTvShow

        Mockito.`when`(movieTvRepository.loadTvShowApi("Newest")).thenReturn(tvshow)
        val tvshowEntities = viewModel.getTvShow("Newest").value?.data
        verify(movieTvRepository).loadTvShowApi("Newest")
        assertNotNull(tvshowEntities)
        assertEquals(10, tvshowEntities?.size)

        viewModel.getTvShow("Newest").observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}