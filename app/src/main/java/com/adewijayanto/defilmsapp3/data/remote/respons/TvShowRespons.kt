package com.adewijayanto.defilmsapp3.data.remote.respons

data class TvShowRespons(
        val page: Int,
        val results: ArrayList<ResultsTvShowItem>,
        val totalPages: Int,
        val totalResults: Int,
)

data class ResultsTvShowItem(
        val id: String,
        val first_air_date: String,
        val overview: String,
        val poster_path: String,
        val backdrop_path: String,
        val original_name: String,
        val vote_average: Float,
        val name: String,
        val genreIds: List<Int>,
        val homepage: String,
        val spoken_languages: String,
        val production_countries: String,
        val vote_count: Int,
)
