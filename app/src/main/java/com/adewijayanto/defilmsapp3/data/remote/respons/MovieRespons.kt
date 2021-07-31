package com.adewijayanto.defilmsapp3.data.remote.respons

data class MovieRespons(
        val page: Int,
        val results: ArrayList<ResultsMovieItem>,
        val totalPages: Int,
        val totalResults: Int,
)

data class ResultsMovieItem(
        val id: String,
        val overview: String,
        val original_title: String,
        val video: Boolean,
        val title: String,
        val poster_path: String,
        val backdrop_path: String,
        val release_date: String,
        val genreIds: List<Int>,
        val homepage: String,
        val spoken_languages: String,
        val vote_average: Float
)
