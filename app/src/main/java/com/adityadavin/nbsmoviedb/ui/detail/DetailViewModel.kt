package com.adityadavin.nbsmoviedb.ui.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase

@SuppressLint("CheckResult")
class DetailViewModel(private val useCase: IMovieUseCase) : ViewModel() {

    fun getDetail(id: Int) = LiveDataReactiveStreams.fromPublisher(useCase.getDetailMovie(id))

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun setInit(){
        _isFavorite.postValue(false)
    }

    fun insertFavorite(movie: DetailMovie) {
        useCase.insertFavorite(movie).subscribe { result ->
            if (result) {
                _isFavorite.postValue(true)
            } else {

            }
        }
    }

    fun deleteFavorite(movie: DetailMovie) {
        useCase.deleteFavorite(movie).subscribe { result ->
            if (result) {
                _isFavorite.postValue(false)
            } else {

            }
        }
    }
}