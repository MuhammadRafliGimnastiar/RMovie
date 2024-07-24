package com.gimnastiar.core.data

import com.gimnastiar.core.data.source.local.LocalDataSource
import com.gimnastiar.core.data.source.remote.RemoteDataSource
import com.gimnastiar.core.data.source.remote.network.ApiResponse
import com.gimnastiar.core.data.source.remote.response.DetailMovieResponse
import com.gimnastiar.core.data.source.remote.response.ResultMovie
import com.gimnastiar.core.domain.model.DetailMovie
import com.gimnastiar.core.domain.model.Movie
import com.gimnastiar.core.domain.repository.IMovieRepository
import com.gimnastiar.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository{
    override fun getPopularMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultMovie>>() {
            override fun loadFromNetwork(data: List<ResultMovie>): Flow<List<Movie>> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<ResultMovie>>> =
                remoteDataSource.getListMovie()

        }.asFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<DetailMovie>> =
        object : NetworkBoundResource<DetailMovie, DetailMovieResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override fun loadFromNetwork(data: DetailMovieResponse): Flow<DetailMovie> =
                DataMapper.mapResponseToDomainDetail(data)

        }.asFlow()

    override fun getMovieFavorite(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovie().map {
            DataMapper.mapEntitiesToDomain(it)
        }


    override suspend fun insertMovie(movie: Movie) =
        localDataSource.insertMovie(DataMapper.mapDomainToEntities(movie))

    override suspend fun deleteMovie(movie: Movie) =
        localDataSource.deleteMovie(DataMapper.mapDomainToEntities(movie))
}