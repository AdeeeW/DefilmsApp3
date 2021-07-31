package com.adewijayanto.defilmsapp3.data.network

import com.adewijayanto.defilmsapp3.BuildConfig.API_KEY
import com.adewijayanto.defilmsapp3.data.remote.respons.MovieDetailResponse
import com.adewijayanto.defilmsapp3.data.remote.respons.MovieRespons
import com.adewijayanto.defilmsapp3.data.remote.respons.TvShowDetailResponse
import com.adewijayanto.defilmsapp3.data.remote.respons.TvShowRespons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    fun getMovies(
            @Query("api_key") api: String = API_KEY,
    ): Call<MovieRespons>

    @GET("tv/top_rated")
    fun getTvShows(
            @Query("api_key") api: String = API_KEY,
    ): Call<TvShowRespons>

    @GET("movie/{movie_id}")
    fun getDetailMovies(
            @Path("movie_id") id: String,
            @Query("api_key") api: String = API_KEY,
    ): Call<MovieDetailResponse>

    @GET("tv/{tv_id}")
    fun getDetailTvShows(
            @Path("tv_id") id: String,
            @Query("api_key") api: String = API_KEY,
    ): Call<TvShowDetailResponse>
}