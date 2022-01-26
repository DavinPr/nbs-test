package com.adityadavin.nbsmoviedb.ui.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase

class FavoriteViewModel (useCase: IMovieUseCase) : ViewModel() {

    val favoriteMovie = LiveDataReactiveStreams.fromPublisher(useCase.getMovieFavorite())

}