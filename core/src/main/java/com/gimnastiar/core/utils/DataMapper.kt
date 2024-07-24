package com.gimnastiar.core.utils

import com.gimnastiar.core.data.source.local.entity.MovieEntity
import com.gimnastiar.core.data.source.remote.response.DetailMovieResponse
import com.gimnastiar.core.data.source.remote.response.ResultMovie
import com.gimnastiar.core.domain.model.DetailMovie
import com.gimnastiar.core.domain.model.Genre
import com.gimnastiar.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapResponseToEntities(input: List<ResultMovie>): List<MovieEntity> {
        val listMovie = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.id,
                it.adult,
                ConstantValue.PATH_IMAGE + it.backdropPath,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                ConstantValue.PATH_IMAGE + it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount
            )
            listMovie.add(movie)
        }
        return listMovie
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        val listMovie = ArrayList<Movie>()
        input.map {
            val movie = Movie(
                it.id,
                it.adult,
                it.backdropPath,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount
            )
            listMovie.add(movie)
        }
        return listMovie
    }

    fun mapResponseToDomain(input: List<ResultMovie>): Flow<List<Movie>> {
        val listMovie = ArrayList<Movie>()
        input.map {
            val movie = Movie(
                it.id,
                it.adult,
                ConstantValue.PATH_IMAGE + it.backdropPath,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                ConstantValue.PATH_IMAGE + it.posterPath,
                it.releaseDate,
                it.title,
                it.video,
                it.voteAverage,
                it.voteCount
            )
            listMovie.add(movie)
        }
        return flowOf(listMovie)
    }

    fun mapDomainToEntities(input: Movie): MovieEntity {
        val movie = MovieEntity(
            input.id,
            input.adult,
            input.backdropPath,
            input.originalLanguage,
            input.originalTitle,
            input.overview,
            input.popularity,
            input.posterPath,
            input.releaseDate,
            input.title,
            input.video,
            input.voteAverage,
            input.voteCount
        )

        return movie
    }

    fun mapResponseToDomainDetail(input: DetailMovieResponse): Flow<DetailMovie> {
        return flowOf(
            DetailMovie(
                input.id,
                input.adult,
                ConstantValue.PATH_IMAGE + input.backdropPath,
                input.budget,
                input.genres.map { Genre(it.id, it.name) },
                input.homepage,
                input.imdbId,
                input.originCountry,
                input.originalLanguage,
                input.originalTitle,
                input.overview,
                input.popularity,
                ConstantValue.PATH_IMAGE + input.posterPath,
                input.releaseDate,
                input.revenue,
                input.runtime,
                input.status,
                input.tagline,
                input.title,
                input.video,
                input.voteAverage,
                input.voteCount
            )
        )
    }

    fun mapDetailToMovie(input: DetailMovie) : Movie {
        return Movie(
            input.id,
            input.adult,
            input.backdropPath,
            input.originalLanguage,
            input.originalTitle,
            input.overview,
            input.popularity,
            input.posterPath,
            input.releaseDate,
            input.title,
            input.video,
            input.voteAverage,
            input.voteCount
        )
    }
}