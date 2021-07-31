package com.adewijayanto.defilmsapp3.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
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
class TvShowFavViewModelTest {
    private lateinit var viewModel: TvShowFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowCatalogueEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowCatalogueEntity>

    @Before
    fun setUp() {
        viewModel = TvShowFavViewModel(movieTvRepository)
    }
    @Test
    fun getFavTvShows() {
        val dummyTvshow = pagedList
        Mockito.`when`(dummyTvshow.size).thenReturn(5)
        val tvshow = MutableLiveData<PagedList<TvShowCatalogueEntity>>()
        tvshow.value = dummyTvshow

        Mockito.`when`(movieTvRepository.getFavoriteTvShow()).thenReturn(tvshow)
        val show = viewModel.getFavTvShows().value
        verify(movieTvRepository).getFavoriteTvShow()
        assertNotNull(show)
        assertEquals(5, show?.size)

        viewModel.getFavTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvshow)
    }

    @Test
    fun setFavTvShow() {
        viewModel.setFavTvShow(DataDummy.generateDummyDetailTvShow())
        verify(movieTvRepository).setFavoriteTvShow(DataDummy.generateDummyDetailTvShow(), true)
        verifyNoMoreInteractions(movieTvRepository)
    }
}