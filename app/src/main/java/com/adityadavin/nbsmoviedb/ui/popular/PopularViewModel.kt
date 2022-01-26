package com.adityadavin.nbsmoviedb.ui.popular

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase

@SuppressLint("CheckResult")
class PopularViewModel(private val useCase: IMovieUseCase) : ViewModel() {

    private var _moviesPopular = MutableLiveData<Resource<List<Movie>>>()
    val moviesPopular: LiveData<Resource<List<Movie>>> get() = _moviesPopular

    fun requestMoviesPopular() {
        useCase.getPopularMoviesOnYear().subscribe {
            _moviesPopular.postValue(it)
        }
    }

    fun searchMoviesPopular(title: String) {
        useCase.getFilteredPopularMovies(title).subscribe {
            _moviesPopular.postValue(it)
        }
    }

    fun requestAllMoviesFromLocal(){
        useCase.getAllLocalPopularMovies().subscribe {
            _moviesPopular.postValue(it)
        }
    }
}