package com.gimnastiar.core.utils

import com.gimnastiar.core.BuildConfig

object ConstantValue {
    const val BASE_URL = BuildConfig.API_URL
    const val API_KEY = BuildConfig.API_KEY
    const val PATH_IMAGE = "https://image.tmdb.org/t/p/w500"

    const val PATH_POPULAR_MOVIE = "${BASE_URL}movie/popular?api_key=$API_KEY"
    const val PATH_DETAIL_MOVIE = "${BASE_URL}movie/{movie_id}?api_key=$API_KEY"

    const val MOVIE_ID = "movie"
}