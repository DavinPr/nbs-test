package com.adityadavin.nbsmoviedb.ui.detail

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase

class DetailViewModel(private val useCase: IMovieUseCase) : ViewModel() {

    fun getDetail(id: Int) = LiveDataReactiveStreams.fromPublisher(useCase.getDetailMovie(id))

}