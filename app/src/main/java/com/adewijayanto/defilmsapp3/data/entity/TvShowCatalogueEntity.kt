package com.adewijayanto.defilmsapp3.data.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tv_show_entity")
@Parcelize
data class TvShowCatalogueEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        val id: String,
        @ColumnInfo(name = "spokenLanguages")
        val spoken_languages: String?,
        @ColumnInfo(name = "firstAirDate")
        val first_air_date: String,
        @ColumnInfo(name = "overview")
        val overview: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "posterPath")
        val poster_path: String,
        @ColumnInfo(name = "backdropPath")
        val backdrop_path: String?,
        @ColumnInfo(name = "voteAverage")
        val vote_average: Float,
        @ColumnInfo(name = "genres")
        var genres: String?,
        @ColumnInfo(name = "homepage")
        val homepage: String?,
        @ColumnInfo(name = "production_countries")
        val production_countries: String?,
        @ColumnInfo(name = "favorite")
        var favorite: Boolean
) : Parcelable
