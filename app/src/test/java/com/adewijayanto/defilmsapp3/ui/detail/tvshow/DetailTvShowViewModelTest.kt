package com.adewijayanto.defilmsapp3.ui.detail.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.adewijayanto.defilmsapp3.data.MovieTvRepository
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
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
class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel

    private val dummyTvShow = DataDummy.generateDummyDetailTvShow()
    private val dummyTvShowId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var tvShowObserver: Observer<Resources<TvShowCatalogueEntity>>

    @Before
    fun setUpTvShow() {
        viewModel = DetailTvShowViewModel(movieTvRepository)
        viewModel.setSelectedTvShow(dummyTvShowId)
    }

    @Test
    fun getTvshow() {
        val dummyTvShowDetail = Resources.success(DataDummy.generateDummyDetailTvShow())
        val tvShow = MutableLiveData<Resources<TvShowCatalogueEntity>>()
        tvShow.value = dummyTvShowDetail

        Mockito.`when`(movieTvRepository.loadDetailTvShowApi(dummyTvShowId)).thenReturn(tvShow)
        viewModel.tvshow.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShowDetail)
    }

    @Test
    fun setFavoriteTvShow() {
        val dummyTvShowFavorite = Resources.success(DataDummy.generateDummyDetailTvShow())
        val tvShow = MutableLiveData<Resources<TvShowCatalogueEntity>>()
        val newState = !dummyTvShow.favorite
        tvShow.value = dummyTvShowFavorite
        Mockito.`when`(movieTvRepository.loadDetailTvShowApi(dummyTvShowId)).thenReturn(tvShow)

        doNothing().`when`(movieTvRepository).setFavoriteTvShow(dummyTvShow, newState)
        viewModel.tvshow.observeForever(tvShowObserver)
        viewModel.setFavoriteTvShow()
        verify(movieTvRepository, Mockito.times(1)).setFavoriteTvShow(dummyTvShow, newState)
    }
}