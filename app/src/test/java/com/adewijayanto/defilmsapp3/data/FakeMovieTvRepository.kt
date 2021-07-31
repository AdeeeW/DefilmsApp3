package com.adewijayanto.defilmsapp3.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.adewijayanto.defilmsapp3.data.entity.MovieCatalogueEntity
import com.adewijayanto.defilmsapp3.data.entity.TvShowCatalogueEntity
import com.adewijayanto.defilmsapp3.data.local.LocalDataSource
import com.adewijayanto.defilmsapp3.data.remote.ApiResponse
import com.adewijayanto.defilmsapp3.data.remote.RemoteDataSource
import com.adewijayanto.defilmsapp3.data.remote.respons.MovieDetailResponse
import com.adewijayanto.defilmsapp3.data.remote.respons.ResultsMovieItem
import com.adewijayanto.defilmsapp3.data.remote.respons.ResultsTvShowItem
import com.adewijayanto.defilmsapp3.data.remote.respons.TvShowDetailResponse
import com.adewijayanto.defilmsapp3.utils.AppExecutors
import com.adewijayanto.defilmsapp3.vo.Resources

class FakeMovieTvRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : MovieTvDataSource {

    override fun loadMovieApi(sort: String): LiveData<Resources<PagedList<MovieCatalogueEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieCatalogueEntity>, List<ResultsMovieItem>>(
                appExecutors
            ) {
            override fun loadFromDb(): LiveData<PagedList<MovieCatalogueEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getDataMovie(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieCatalogueEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsMovieItem>>> =
                remoteDataSource.loadMovieApi()

            override fun saveCallResult(data: List<ResultsMovieItem>) {
                val movieList = ArrayList<MovieCatalogueEntity>()
                for (response in data) {
                    val movie = MovieCatalogueEntity(
                        id = response.id,
                        overview = response.overview,
                        spoken_languages = response.spoken_languages,
                        title = response.title,
                        poster_path = response.poster_path,
                        backdrop_path = response.backdrop_path,
                        release_date = response.release_date,
                        vote_average = response.vote_average / 2,
                        genres = "",
                        homepage = response.homepage,
                        favorite = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovieTv(movieList)
            }
        }.asLiveData()
    }

    override fun loadTvShowApi(sort: String): LiveData<Resources<PagedList<TvShowCatalogueEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowCatalogueEntity>, List<ResultsTvShowItem>>(
                appExecutors
            ) {
            override fun loadFromDb(): LiveData<PagedList<TvShowCatalogueEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(localDataSource.getDataTV(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowCatalogueEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultsTvShowItem>>> =
                remoteDataSource.loadTvShowApi()

            override fun saveCallResult(data: List<ResultsTvShowItem>) {
                val movieList = ArrayList<TvShowCatalogueEntity>()
                for (response in data) {
                    val movie = TvShowCatalogueEntity(
                        id = response.id,
                        spoken_languages = response.spoken_languages,
                        first_air_date = response.first_air_date,
                        overview = response.overview,
                        name = response.name,
                        poster_path = response.poster_path,
                        backdrop_path = response.backdrop_path,
                        vote_average = response.vote_average,
                        genres = "",
                        homepage = response.homepage,
                        production_countries = response.production_countries,
                        favorite = false
                    )
                    movieList.add(movie)
                }
                localDataSource.insertTvShow(movieList)
            }
        }.asLiveData()
    }

    override fun loadDetailMovieApi(movieId: String): LiveData<Resources<MovieCatalogueEntity>> {
        return object :
            NetworkBoundResource<MovieCatalogueEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<MovieCatalogueEntity> =
                localDataSource.getMovieById(movieId.toInt())

            override fun shouldFetch(data: MovieCatalogueEntity?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.loadMovieDetailApi(movieId)

            override fun saveCallResult(data: MovieDetailResponse) {
                val genres = StringBuilder().append("")
                val spoken_languages = StringBuilder().append("")

                for (i in data.genres.indices) {
                    if (i < data.genres.size - 1) {
                        genres.append("${data.genres[i].name}, ")
                    } else {
                        genres.append(data.genres[i].name)
                    }
                }

                for (i in data.spoken_languages.indices) {
                    if (i < data.spoken_languages.size - 1) {
                        spoken_languages.append("${data.spoken_languages[i].english_name}, ")
                    } else {
                        spoken_languages.append(data.spoken_languages[i].english_name)
                    }
                }

                val movie = MovieCatalogueEntity(
                    id = data.id,
                    spoken_languages = spoken_languages.toString(),
                    overview = data.overview,
                    title = data.title,
                    poster_path = data.poster_path,
                    backdrop_path = data.backdrop_path,
                    release_date = data.release_date,
                    vote_average = data.vote_average / 2,
                    genres = genres.toString(),
                    homepage = data.homepage,
                    favorite = false
                )
                localDataSource.updateMovie(movie, false)
            }
        }.asLiveData()
    }

    override fun loadDetailTvShowApi(tvShowId: String): LiveData<Resources<TvShowCatalogueEntity>> {
        return object :
            NetworkBoundResource<TvShowCatalogueEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDb(): LiveData<TvShowCatalogueEntity> =
                localDataSource.getTvShowById(tvShowId.toInt())

            override fun shouldFetch(data: TvShowCatalogueEntity?): Boolean =
                data != null && data.genres == ""

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> =
                remoteDataSource.loadTvShowDetailApi(tvShowId)

            override fun saveCallResult(data: TvShowDetailResponse) {
                val genres = StringBuilder().append("")
                val spoken_languages = StringBuilder().append("")
                val production_countries = StringBuilder().append("")

                for (i in data.genres.indices) {
                    if (i < data.genres.size - 1) {
                        genres.append("${data.genres[i].name}, ")
                    } else {
                        genres.append(data.genres[i].name)
                    }
                }

                for (i in data.spoken_languages.indices) {
                    if (i < data.spoken_languages.size - 1) {
                        spoken_languages.append("${data.spoken_languages[i].english_name}, ")
                    } else {
                        spoken_languages.append(data.spoken_languages[i].english_name)
                    }
                }

                for (i in data.production_countries.indices) {
                    if (i < data.production_countries.size - 1) {
                        production_countries.append("${data.production_countries[i].name}, ")
                    } else {
                        production_countries.append(data.production_countries[i].name)
                    }
                }

                val tvShow = TvShowCatalogueEntity(
                    id = data.id,
                    spoken_languages = spoken_languages.toString(),
                    first_air_date = data.first_air_date,
                    overview = data.overview,
                    name = data.name,
                    poster_path = data.poster_path,
                    backdrop_path = data.backdrop_path,
                    vote_average = data.vote_average / 2,
                    genres = genres.toString(),
                    homepage = data.homepage,
                    production_countries = production_countries.toString(),
                    favorite = false
                )
                localDataSource.updateTvShow(tvShow, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<MovieCatalogueEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowCatalogueEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }


    override fun setFavoriteMovie(movie: MovieCatalogueEntity, state: Boolean) = localDataSource.setFavoriteMovie(movie, state)



    override fun setFavoriteTvShow(tvShow: TvShowCatalogueEntity, state: Boolean) = localDataSource.setFavoriteTvShow(tvShow, state)

}