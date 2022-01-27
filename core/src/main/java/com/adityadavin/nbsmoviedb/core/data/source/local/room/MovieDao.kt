package com.adityadavin.nbsmoviedb.core.data.source.local.room

import androidx.room.*
import com.adityadavin.nbsmoviedb.core.data.source.local.entity.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_banner")
    fun getMoviesBanner(): Flowable<List<MovieBannerEntity>>

    @Query("SELECT * FROM movie_popular")
    fun getMoviesPopular(): Flowable<List<MoviePopularEntity>>

    @Query("SELECT * FROM movie_coming_soon")
    fun getMoviesComingSoon(): Flowable<List<MovieComingSoonEntity>>

    @Query("SELECT * FROM movie_popular_on_year")
    fun getMoviesPopularOnYear(): Flowable<List<MoviePopularOnYearEntity>>

    @Query("SELECT * FROM movie_popular_on_year WHERE title LIKE '%' || :title || '%'")
    fun getFilteredMoviePopular(title: String): Flowable<List<MoviePopularOnYearEntity>>

    @Query("SELECT * FROM movie_favorite")
    fun getMoviesFavorite(): Flowable<List<MovieFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesBanner(movies: List<MovieBannerEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesPopular(movies: List<MoviePopularEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesComingSoon(movies: List<MovieComingSoonEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesPopularOnYear(movies: List<MoviePopularOnYearEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieFavorite(movie: MovieFavoriteEntity): Completable

    @Delete
    fun deleteMovieFavorite(movie: MovieFavoriteEntity): Completable

    @Query("DELETE FROM movie_banner")
    fun deleteAllMoviesBanner(): Completable

    @Query("DELETE FROM movie_popular")
    fun deleteAllMoviesPopular(): Completable

    @Query("DELETE FROM movie_coming_soon")
    fun deleteAllMoviesComingSoon(): Completable

    @Query("DELETE FROM movie_popular_on_year")
    fun deleteAllMoviesPopularOnYear(): Completable

    @Query("SELECT EXISTS(SELECT * FROM movie_favorite WHERE movieId = :id)")
    fun isFavorite(id: Int): Single<Boolean>

}