package com.adewijayanto.defilmsapp3.data.remote.respons

data class TvShowDetailResponse(
    val id: String,
    val first_air_date: String,
    val overview: String,
    val spoken_languages: ArrayList<SpokeTvLanguages>,
    val name: String,
    val production_countries: ArrayList<Country>,
    val genres: ArrayList<GenreTvEntity>,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Float,
    val homepage: String,
)

data class SpokeTvLanguages(
    val english_name: String
)

data class GenreTvEntity(
    val name: String
)

data class Country(
    val name: String
)