package com.adityadavin.nbsmoviedb.core.domain.usecase

import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.CategoryMovie
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import io.reactivex.Flowable

interface IMovieUseCase {
    fun getMovieByCategory() : Flowable<List<CategoryMovie>>

    fun getPopularMoviesOnYear(): Flowable<Resource<List<Movie>>>

    fun getFilteredPopularMovies(title: String): Flowable<Resource<List<Movie>>>

    fun getAllLocalPopularMovies(): Flowable<Resource<List<Movie>>>

    fun getDetailMovie(id : Int) : Flowable<Resource<DetailMovie>>
}