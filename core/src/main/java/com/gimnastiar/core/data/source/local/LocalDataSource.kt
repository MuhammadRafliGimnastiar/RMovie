package com.gimnastiar.core.data.source.local

import com.gimnastiar.core.data.source.local.entity.MovieEntity
import com.gimnastiar.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dao: MovieDao) {
    fun getFavoriteMovie() : Flow<List<MovieEntity>> = dao.getFavoriteMovie()

    suspend fun insertMovie(movie: MovieEntity) = dao.insertFavoriteMovie(movie)

    suspend fun deleteMovie(movie: MovieEntity) = dao.deleteFavoriteMovie(movie)
}