package com.adityadavin.nbsmoviedb.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.domain.model.CategoryMovie
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase

class HomeViewModel(private val useCase: IMovieUseCase) : ViewModel() {

    private var _moviesByCategory = MutableLiveData<List<CategoryMovie>>()
    val moviesByCategory: LiveData<List<CategoryMovie>> get() = _moviesByCategory

    @SuppressLint("CheckResult")
    fun requestMoviesByCategory() {
        useCase.getMovieByCategory().subscribe {
            _moviesByCategory.postValue(it)
        }
    }

}