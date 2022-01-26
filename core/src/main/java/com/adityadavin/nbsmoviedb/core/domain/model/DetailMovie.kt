package com.adityadavin.nbsmoviedb.core.domain.model

data class DetailMovie(

    val title: String? = null,
    val backdropPath: String? = null,
    val genres: String? = null,
    val id: Int = 0,
    val overview: String? = null,
    val runtime: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val tagline: String? = null,
    var cast: List<Casts> = listOf(),
    var isCombineSuccess : Boolean = false,
    var message : String? = null
)

data class Genres(

    val name: String? = null,
    val id: Int = 0
)

data class Casts(

    val castId: Int = 0,
    val character: String? = null,
    val name: String? = null,
    val profilePath: String? = null,
    val id: Int = 0,
)