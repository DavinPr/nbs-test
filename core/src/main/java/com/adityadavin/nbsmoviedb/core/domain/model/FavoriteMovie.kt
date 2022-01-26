package com.adityadavin.nbsmoviedb.core.domain.model

data class FavoriteMovie(

    val title: String? = null,
    val backdropPath: String? = null,
    val genres: String? = null,
    val movieId: Int = 0,
    val overview: String? = null,
    val runtime: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val tagline: String? = null,
    val _id: Int = 0
)
