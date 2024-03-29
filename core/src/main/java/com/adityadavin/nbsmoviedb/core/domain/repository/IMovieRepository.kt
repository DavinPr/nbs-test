package com.adityadavin.nbsmoviedb.core.domain.repository

import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.model.FavoriteMovie
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IMovieRepository {
    fun getBannerMovies(): Flowable<Resource<List<Movie>>>

    fun getPopularMovies(): Flowable<Resource<List<Movie>>>

    fun getComingSoonMovies(year: Int): Flowable<Resource<List<Movie>>>

    fun getPopularMoviesOnYear(year: Int): Flowable<Resource<List<Movie>>>

    fun getFilteredPopularMovies(title: String): Flowable<Resource<List<Movie>>>

    fun getAllLocalPopularMovies(): Flowable<Resource<List<Movie>>>

    fun getDetailMovie(id: Int): Flowable<Resource<DetailMovie>>

    fun getMovieFavorite(): Flowable<Resource<List<FavoriteMovie>>>

    fun getFilteredMovieFavorite(title: String): Flowable<Resource<List<FavoriteMovie>>>

    fun insertFavorite(movie: FavoriteMovie): Completable

    fun deleteFavorite(movie: FavoriteMovie): Completable

    fun isFavorite(id: Int): Single<Boolean>
}