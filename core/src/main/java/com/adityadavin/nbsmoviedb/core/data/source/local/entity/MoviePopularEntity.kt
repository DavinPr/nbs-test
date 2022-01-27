package com.adityadavin.nbsmoviedb.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_popular")
data class MoviePopularEntity(
    @ColumnInfo(name = "overview")
    val overview: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "posterPath")
    val posterPath: String? = null,
    @ColumnInfo(name = "backdropPath")
    val backdropPath: String? = null,
    @ColumnInfo(name = "releaseDate")
    val releaseDate: String? = null,
    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double = 0.0,
    @ColumnInfo(name = "movieId")
    @PrimaryKey
    val movieId: Int = 0,
)
