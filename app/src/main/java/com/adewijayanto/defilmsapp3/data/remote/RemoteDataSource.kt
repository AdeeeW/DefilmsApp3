package com.adewijayanto.defilmsapp3.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adewijayanto.defilmsapp3.BuildConfig.API_KEY
import com.adewijayanto.defilmsapp3.data.network.ApiConfig
import com.adewijayanto.defilmsapp3.data.remote.respons.*
import com.adewijayanto.defilmsapp3.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        const val TAG = "RemoteDataSource"
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource()
                }
    }

    fun loadMovieApi(): LiveData<ApiResponse<List<ResultsMovieItem>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<ResultsMovieItem>>>()
        val client = ApiConfig.getApiService().getMovies(API_KEY)

        client.enqueue(object : Callback<MovieRespons> {
            override fun onResponse(call: Call<MovieRespons>, response: Response<MovieRespons>) {
                resultMovies.value = ApiResponse.success(response.body()?.results as List<ResultsMovieItem>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieRespons>, t: Throwable) {
                Log.e("RemoteDataSource", "getMovies onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultMovies
    }

    fun loadTvShowApi(): LiveData<ApiResponse<List<ResultsTvShowItem>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<ResultsTvShowItem>>>()
        val client = ApiConfig.getApiService().getTvShows(API_KEY)

        client.enqueue(object : Callback<TvShowRespons> {
            override fun onResponse(call: Call<TvShowRespons>, response: Response<TvShowRespons>) {
                resultTvShows.value = ApiResponse.success(response.body()?.results as List<ResultsTvShowItem>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowRespons>, t: Throwable) {
                Log.e(TAG, "getTvShows onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultTvShows
    }

    fun loadMovieDetailApi(movieId: String): LiveData<ApiResponse<MovieDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        val client = ApiConfig.getApiService().getDetailMovies(movieId, API_KEY)

        client.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                resultDetailMovie.value = ApiResponse.success(response.body() as MovieDetailResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, "getMovieDetail onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultDetailMovie
    }

    fun loadTvShowDetailApi(tvShowId: String): LiveData<ApiResponse<TvShowDetailResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        val client = ApiConfig.getApiService().getDetailTvShows(tvShowId, API_KEY)

        client.enqueue(object : Callback<TvShowDetailResponse> {
            override fun onResponse(call: Call<TvShowDetailResponse>, response: Response<TvShowDetailResponse>) {
                resultDetailTvShow.value = ApiResponse.success(response.body() as TvShowDetailResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                Log.e(TAG, "getDetailTvShow onFailure : ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultDetailTvShow
    }
}