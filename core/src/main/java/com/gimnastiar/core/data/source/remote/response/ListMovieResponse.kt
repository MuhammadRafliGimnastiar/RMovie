package com.gimnastiar.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ListMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)