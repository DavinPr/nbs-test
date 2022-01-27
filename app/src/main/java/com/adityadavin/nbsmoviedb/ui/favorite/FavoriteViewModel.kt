package com.adityadavin.nbsmoviedb.ui.favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.FavoriteMovie
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase
import com.adityadavin.nbsmoviedb.utils.Event

@SuppressLint("CheckResult")
class FavoriteViewModel(private val useCase: IMovieUseCase) : ViewModel() {

    private var _favoriteMovie = MutableLiveData<Resource<List<FavoriteMovie>>>()
    val favoriteMovie: LiveData<Resource<List<FavoriteMovie>>> get() = _favoriteMovie

    fun requestMoviesFavorite() {
        useCase.getMovieFavorite().subscribe {
            _favoriteMovie.postValue(it)
        }
    }

    fun searchMoviesFavorite(title: String) {
        useCase.getFilteredMovieFavorite(title).subscribe {
            _favoriteMovie.postValue(it)
        }
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event
    val clearEvent = _event.postValue(Event.NoEvent)

    fun deleteFavorite(movie: FavoriteMovie) {
        useCase.deleteFavorite(movie).subscribe({
            _event.postValue(Event.ShowToast("Delete Success"))
            requestMoviesFavorite()
        }) { error ->
            _event.postValue(Event.ShowToast("Delete Failed!"))
            Log.e("Delete Favorite", error.message.toString())
        }
    }

}