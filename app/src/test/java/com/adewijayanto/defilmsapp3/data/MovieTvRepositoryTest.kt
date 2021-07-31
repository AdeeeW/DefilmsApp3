package com.adewijayanto.defilmsapp3.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.data.local.LocalDataSource
import com.adewijayanto.defilmsapp3.data.local.database.MovieTvDao
import com.adewijayanto.defilmsapp3.data.remote.RemoteDataSource
import com.adewijayanto.defilmsapp3.utils.AppExecutors
import com.adewijayanto.defilmsapp3.utils.DataDummy
import com.adewijayanto.defilmsapp3.utils.LiveDataTestUtil
import com.adewijayanto.defilmsapp3.utils.PagedListUtil
import com.adewijayanto.defilmsapp3.vo.Resources
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import junit.framework.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MovieTvRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val dao = Mockito.mock(MovieTvDao::class.java)
    private val movieCatalogueRepository = FakeMovieTvRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateDummyMovies()
    private val tvShowResponses = DataDummy.generateDummyTvShow()

    private val tvShowId = tvShowResponses[0].id
    private val movieId = movieResponses[0].id

    private val movieDetail = DataDummy.generateDummyDetailMovie()
    private val tvShowDetail = DataDummy.generateDummyDetailTvShow()

    @Test
    fun loadMovieApi() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieCatalogueEntity>
        Mockito.`when`(local.getDataMovie("Newest")).thenReturn(dataSourceFactory)
        movieCatalogueRepository.loadMovieApi("Newest")

        val movieEntities =
            Resources.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getDataMovie("Newest")
        Assert.assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun loadTvShowApi() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowCatalogueEntity>
        Mockito.`when`(local.getDataTV("Newest")).thenReturn(dataSourceFactory)
        movieCatalogueRepository.loadTvShowApi("Newest")

        val tvshwoEntities =
            Resources.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getDataTV("Newest")
        Assert.assertNotNull(tvshwoEntities)
        assertEquals(tvShowResponses.size.toLong(), tvshwoEntities.data?.size?.toLong())
    }

    @Test
    fun loadDetailMovieApi() {
        val dummyDetailMovie = MutableLiveData<MovieCatalogueEntity>()
        dummyDetailMovie.value = DataDummy.generateDummyDetailMovie()
        Mockito.`when`(local.getMovieById(movieId.toInt())).thenReturn(dummyDetailMovie)

        val movieDetailEntity =
            LiveDataTestUtil.getValue(movieCatalogueRepository.loadDetailMovieApi(movieId))
        verify(local).getMovieById(movieId.toInt())
        assertNotNull(movieDetailEntity)
        assertEquals(movieDetail.id, movieDetailEntity.data?.id)
    }

    @Test
    fun loadDetailTvShowApi() {
        val dummyDetailTvShow = MutableLiveData<TvShowCatalogueEntity>()
        dummyDetailTvShow.value = DataDummy.generateDummyDetailTvShow()
        Mockito.`when`(local.getTvShowById(tvShowId.toInt())).thenReturn(dummyDetailTvShow)

        val tvshowDetailEntity =
            LiveDataTestUtil.getValue(movieCatalogueRepository.loadDetailTvShowApi(tvShowId))
        verify(local).getTvShowById(tvShowId.toInt())
        assertNotNull(tvshowDetailEntity)
        assertEquals(tvShowDetail.id, tvshowDetailEntity.data?.id)
    }

    @Test
    fun setFavoriteMovie() {
        val dataDummy = DataDummy.generateDummyDetailMovie()

        movieCatalogueRepository.setFavoriteMovie(dataDummy, true)
        verify(local).setFavoriteMovie(dataDummy, true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun setFavoriteTvShow() {
        val dataDummy = DataDummy.generateDummyDetailTvShow()

        movieCatalogueRepository.setFavoriteTvShow(dataDummy, true)
        verify(local).setFavoriteTvShow(dataDummy, true)
        verifyNoMoreInteractions(local)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieCatalogueEntity>
        Mockito.`when`(local.getFavoriteMovie()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getFavoriteMovie()

        val movieEntities =
            Resources.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size, movieEntities.data?.size)
    }

    @Test
    fun getFavoriteTvShow() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowCatalogueEntity>
        Mockito.`when`(local.getFavoriteTvShow()).thenReturn(dataSourceFactory)
        movieCatalogueRepository.getFavoriteTvShow()

        val tvShowEntities =
            Resources.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getFavoriteTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size, tvShowEntities.data?.size)
    }
}