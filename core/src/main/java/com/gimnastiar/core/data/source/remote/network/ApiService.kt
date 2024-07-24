package com.gimnastiar.core.data.source.remote.network

import com.gimnastiar.core.data.source.remote.response.DetailMovieResponse
import com.gimnastiar.core.data.source.remote.response.ListMovieResponse
import com.gimnastiar.core.utils.ConstantValue
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET(ConstantValue.PATH_POPULAR_MOVIE)
    suspend fun getListPopular(
    ) : ListMovieResponse

    @GET(ConstantValue.PATH_DETAIL_MOVIE)
    suspend fun getDetail(
        @Path("movie_id") movieId : Int
    ) : DetailMovieResponse
}