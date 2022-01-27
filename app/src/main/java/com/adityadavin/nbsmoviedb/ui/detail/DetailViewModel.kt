package com.adityadavin.nbsmoviedb.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase
import com.adityadavin.nbsmoviedb.utils.Event

@SuppressLint("CheckResult")
class DetailViewModel(private val useCase: IMovieUseCase) : ViewModel() {

    private val _detail = MutableLiveData<Resource<DetailMovie>>()
    val detail: LiveData<Resource<DetailMovie>> get() = _detail


    fun requestDetail(id: Int) {
        useCase.getDetailMovie(id).subscribe {
            _detail.postValue(it)
        }
    }

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event
    val clearEvent = _event.postValue(Event.NoEvent)

    fun insertFavorite(movie: DetailMovie) {
        useCase.insertFavorite(movie).subscribe({
            _isFavorite.postValue(true)
            _event.postValue(Event.ShowToast("Insert Success!"))
        }) { error ->
            _event.postValue(Event.ShowToast("Insert Failed!"))
            Log.e("Insert Favorite", error.message.toString())
        }
    }


    fun deleteFavorite(movie: DetailMovie) {
        useCase.deleteFavoriteFromDetail(movie).subscribe({
            _isFavorite.postValue(false)
            _event.postValue(Event.ShowToast("Delete Success!"))
        }) { error ->
            _event.postValue(Event.ShowToast("Delete Failed!"))
            Log.e("Delete Favorite", error.message.toString())
        }
    }

    fun checkFavorite(id: Int) {
        useCase.isFavorite(id).subscribe({ state ->
            _isFavorite.postValue(state)
            Log.d("IsFavorite", state.toString())
        }) {
            Log.d("IsFavorite", it.message.toString())
        }
    }
}