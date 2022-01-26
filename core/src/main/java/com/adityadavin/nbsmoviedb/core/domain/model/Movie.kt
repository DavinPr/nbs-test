package com.adityadavin.nbsmoviedb.core.domain.model

data class Movie(
    val overview: String? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val id: Int = 0,
)
