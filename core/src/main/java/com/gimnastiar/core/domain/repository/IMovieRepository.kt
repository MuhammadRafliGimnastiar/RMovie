package com.gimnastiar.core.domain.repository

import com.gimnastiar.core.data.Resource
import com.gimnastiar.core.domain.model.DetailMovie
import com.gimnastiar.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovie() : Flow<Resource<List<Movie>>>

    fun getDetailMovie(id: Int) : Flow<Resource<DetailMovie>>

    fun getMovieFavorite() : Flow<List<Movie>>

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

}