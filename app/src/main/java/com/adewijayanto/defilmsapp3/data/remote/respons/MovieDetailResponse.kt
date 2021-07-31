package com.adewijayanto.defilmsapp3.data.remote.respons

data class MovieDetailResponse(
    val id: String,
    val overview: String,
    val spoken_languages: ArrayList<SpokeLanguages>,
    val original_title: String,
    val title: String,
    val genres: ArrayList<GenreEntity>,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val popularity: Double,
    val vote_average: Float,
    val homepage: String,
)

data class SpokeLanguages(
    val english_name: String
)

data class GenreEntity(
    val name: String
)
