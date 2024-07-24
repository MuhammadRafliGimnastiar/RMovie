package com.gimnastiar.core.domain.usecase

import com.gimnastiar.core.data.Resource
import com.gimnastiar.core.domain.model.DetailMovie
import com.gimnastiar.core.domain.model.Movie
import com.gimnastiar.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val repository: IMovieRepository) : MovieUseCase {
    override fun getPopularMovie(): Flow<Resource<List<Movie>>> =
        repository.getPopularMovie()

    override fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>> =
        repository.getDetailMovie(id)

    override fun getMovieFavorite(): Flow<List<Movie>> =
        repository.getMovieFavorite()

    override suspend fun insertMovie(movie: Movie) =
        repository.insertMovie(movie)

    override suspend fun deleteMovie(movie: Movie) =
        repository.deleteMovie(movie)

}