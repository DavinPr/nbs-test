package com.adityadavin.nbsmoviedb.core.data.source.remote.network

import com.adityadavin.nbsmoviedb.core.data.source.remote.response.CastMovieResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.DetailMovieResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.MovieResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getBannerMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") adult: Boolean = false,
        @Query("include_video") video: Boolean = false,
        @Query("page") page: Int = 1
    ): Flowable<MovieResponse>

    @GET("discover/movie")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") adult: Boolean = false,
        @Query("include_video") video: Boolean = false,
        @Query("page") page: Int = 1
    ): Flowable<MovieResponse>

    @GET("discover/movie")
    fun getComingSoonMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") adult: Boolean = false,
        @Query("include_video") video: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("year") year: Int
    ): Flowable<MovieResponse>

    @GET("discover/movie")
    fun getPopularMoviesOnYear(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") adult: Boolean = false,
        @Query("include_video") video: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("year") year: Int
    ): Flowable<MovieResponse>

    @GET("movie/{id}")
    fun getDetailMovie(
        @Path("id") id : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): Flowable<DetailMovieResponse>

    @GET("movie/{id}/credits")
    fun getMovieCast(
        @Path("id") id : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
    ): Flowable<CastMovieResponse>

}