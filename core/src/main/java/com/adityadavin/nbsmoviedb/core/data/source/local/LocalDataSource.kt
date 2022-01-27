package com.adityadavin.nbsmoviedb.core.data.source.local

import com.adityadavin.nbsmoviedb.core.data.source.local.entity.*
import com.adityadavin.nbsmoviedb.core.data.source.local.room.MovieDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSource(private val movieDao: MovieDao) {

    fun getMoviesBanner(): Flowable<List<MovieBannerEntity>> = movieDao.getMoviesBanner()

    fun getMoviesPopular(): Flowable<List<MoviePopularEntity>> = movieDao.getMoviesPopular()

    fun getMoviesComingSoon(): Flowable<List<MovieComingSoonEntity>> =
        movieDao.getMoviesComingSoon()

    fun getMoviesPopularOnYear(): Flowable<List<MoviePopularOnYearEntity>> =
        movieDao.getMoviesPopularOnYear()

    fun getFilteredMoviePopular(title: String): Flowable<List<MoviePopularOnYearEntity>> =
        movieDao.getFilteredMoviePopular(title)

    fun getMoviesFavorite(): Flowable<List<MovieFavoriteEntity>> = movieDao.getMoviesFavorite()

    fun getFilteredMovieFavorite(title: String): Flowable<List<MovieFavoriteEntity>> =
        movieDao.getFilteredMovieFavorite(title)

    fun insertMoviesBanner(movies: List<MovieBannerEntity>): Completable =
        movieDao.insertMoviesBanner(movies)

    fun insertMoviesPopular(movies: List<MoviePopularEntity>): Completable =
        movieDao.insertMoviesPopular(movies)

    fun insertMoviesComingSoon(movies: List<MovieComingSoonEntity>): Completable =
        movieDao.insertMoviesComingSoon(movies)

    fun insertMoviesPopularOnYear(movies: List<MoviePopularOnYearEntity>): Completable =
        movieDao.insertMoviesPopularOnYear(movies)

    fun insertMovieFavorite(movie: MovieFavoriteEntity): Completable =
        movieDao.insertMovieFavorite(movie)

    fun deleteAllMoviesBanner(): Completable = movieDao.deleteAllMoviesBanner()

    fun deleteAllMoviesPopular(): Completable = movieDao.deleteAllMoviesPopular()

    fun deleteAllMoviesComingSoon(): Completable = movieDao.deleteAllMoviesComingSoon()

    fun deleteAllMoviesPopularOnYear(): Completable = movieDao.deleteAllMoviesPopularOnYear()

    fun deleteMovieFavorite(movie: MovieFavoriteEntity): Completable =
        movieDao.deleteMovieFavorite(movie)


    fun isFavorite(id: Int): Single<Boolean> = movieDao.isFavorite(id)
}