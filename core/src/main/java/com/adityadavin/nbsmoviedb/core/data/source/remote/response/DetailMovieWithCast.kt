package com.adityadavin.nbsmoviedb.core.data.source.remote.response

data class DetailMovieWithCast(
    val detailMovie: DetailMovieResponse,
    val cast: CastMovieResponse
)