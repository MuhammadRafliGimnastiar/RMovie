package com.gimnastiar.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gimnastiar.core.data.source.remote.response.BelongsToCollection
import com.gimnastiar.core.data.source.remote.response.Genre
import com.gimnastiar.core.data.source.remote.response.ProductionCompany
import com.gimnastiar.core.data.source.remote.response.ProductionCountry
import com.gimnastiar.core.data.source.remote.response.SpokenLanguage

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)