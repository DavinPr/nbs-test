package com.adityadavin.nbsmoviedb.di

import com.adityadavin.nbsmoviedb.core.domain.usecase.IMovieUseCase
import com.adityadavin.nbsmoviedb.core.domain.usecase.MovieInteractor
import com.adityadavin.nbsmoviedb.ui.detail.DetailViewModel
import com.adityadavin.nbsmoviedb.ui.home.HomeViewModel
import com.adityadavin.nbsmoviedb.ui.popular.PopularViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { PopularViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}