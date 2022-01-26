package com.adityadavin.nbsmoviedb.core.utils

import com.adityadavin.nbsmoviedb.core.BuildConfig
import com.adityadavin.nbsmoviedb.core.data.source.local.entity.MovieBannerEntity
import com.adityadavin.nbsmoviedb.core.data.source.local.entity.MovieComingSoonEntity
import com.adityadavin.nbsmoviedb.core.data.source.local.entity.MoviePopularEntity
import com.adityadavin.nbsmoviedb.core.data.source.local.entity.MoviePopularOnYearEntity
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.CastItemResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.DetailMovieResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.MovieResultResponse
import com.adityadavin.nbsmoviedb.core.domain.model.Casts
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.model.Movie

fun MovieResultResponse.toBannerEntity(): MovieBannerEntity {
    return MovieBannerEntity(
        overview,
        title,
        BuildConfig.IMAGE_URL + posterPath,
        BuildConfig.IMAGE_URL + backdropPath,
        releaseDate,
        voteAverage,
        id
    )
}

fun MovieResultResponse.toComingSoonEntity(): MovieComingSoonEntity {
    return MovieComingSoonEntity(
        overview,
        title,
        BuildConfig.IMAGE_URL + posterPath,
        BuildConfig.IMAGE_URL + backdropPath,
        releaseDate,
        voteAverage,
        id
    )
}

fun MovieResultResponse.toPopularEntity(): MoviePopularEntity {
    return MoviePopularEntity(
        overview,
        title,
        BuildConfig.IMAGE_URL + posterPath,
        BuildConfig.IMAGE_URL + backdropPath,
        releaseDate,
        voteAverage,
        id
    )
}

fun MovieResultResponse.toPopularOnYearEntity(): MoviePopularOnYearEntity {
    return MoviePopularOnYearEntity(
        overview,
        title,
        BuildConfig.IMAGE_URL + posterPath,
        BuildConfig.IMAGE_URL + backdropPath,
        releaseDate,
        voteAverage,
        id
    )
}

fun MovieBannerEntity.toDomain(): Movie {
    return Movie(
        overview, title, posterPath, backdropPath, releaseDate, voteAverage, movieId
    )
}

fun MoviePopularEntity.toDomain(): Movie {
    return Movie(
        overview, title, posterPath, backdropPath, releaseDate, voteAverage, movieId
    )
}

fun MovieComingSoonEntity.toDomain(): Movie {
    return Movie(
        overview, title, posterPath, backdropPath, releaseDate, voteAverage, movieId
    )
}

fun MoviePopularOnYearEntity.toDomain(): Movie {
    return Movie(
        overview, title, posterPath, backdropPath, releaseDate, voteAverage, movieId
    )
}

fun DetailMovieResponse.toDomain(): DetailMovie {
    return DetailMovie(
        title,
        BuildConfig.IMAGE_URL + backdropPath,
        genres.map { it.name }.joinToString(),
        id,
        overview,
        runtime.runtimeFormat(),
        BuildConfig.IMAGE_URL + posterPath,
        releaseDate,
        voteAverage,
        tagline
    )
}


fun CastItemResponse.toDomain(): Casts {
    return Casts(castId, character, name, BuildConfig.IMAGE_URL + profilePath)
}