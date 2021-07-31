package com.adewijayanto.defilmsapp3.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "Newest"

    fun getSortedMovieQuery(filter: String): SimpleSQLiteQuery {
        val baseQuery = StringBuilder().append("SELECT * FROM movie_entity")
        when(filter){
            NEWEST -> baseQuery.append(" ORDER BY releaseDate DESC")
        }

        return SimpleSQLiteQuery(baseQuery.toString())
    }

    fun getSortedTvShowQuery(filter: String): SimpleSQLiteQuery {
        val baseQuery = StringBuilder().append("SELECT * FROM tv_show_entity")
        when(filter){
            NEWEST -> baseQuery.append(" ORDER BY firstAirDate DESC")
        }

        return SimpleSQLiteQuery(baseQuery.toString())
    }
}