package com.adityadavin.nbsmoviedb.core.domain.usecase

import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.CategoryMovie
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import com.adityadavin.nbsmoviedb.core.domain.repository.IMovieRepository
import com.adityadavin.nbsmoviedb.core.utils.BANNER_TYPE
import com.adityadavin.nbsmoviedb.core.utils.COMING_SOON_MOVIE
import com.adityadavin.nbsmoviedb.core.utils.HORIZONTAL_LIST_TYPE
import com.adityadavin.nbsmoviedb.core.utils.POPULAR_MOVIE
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*

class MovieInteractor(private val repository: IMovieRepository) : IMovieUseCase {

    private val mCompositeDisposable = CompositeDisposable()

    override fun getMovieByCategory(): Flowable<List<CategoryMovie>> {
        val result = PublishSubject.create<List<CategoryMovie>>()
        val disposable = Flowable.zip(
            repository.getBannerMovies(),
            repository.getPopularMovies(),
            repository.getComingSoonMovies(Calendar.getInstance().get(Calendar.YEAR) + 1),
            { t1, t2, t3 ->
                listOf(
                    CategoryMovie(BANNER_TYPE, null, t1),
                    CategoryMovie(HORIZONTAL_LIST_TYPE, POPULAR_MOVIE, t2),
                    CategoryMovie(HORIZONTAL_LIST_TYPE, COMING_SOON_MOVIE, t3)
                )
            }
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe {
                result.onNext(it)
            }
        mCompositeDisposable.add(disposable)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getPopularMoviesOnYear(): Flowable<Resource<List<Movie>>> =
        repository.getPopularMoviesOnYear(Calendar.getInstance().get(Calendar.YEAR))

    override fun getFilteredPopularMovies(title: String): Flowable<Resource<List<Movie>>> =
        repository.getFilteredPopularMovies(title)

    override fun getAllLocalPopularMovies(): Flowable<Resource<List<Movie>>> =
        repository.getAllLocalPopularMovies()

    override fun getDetailMovie(id: Int): Flowable<Resource<DetailMovie>> =
        repository.getDetailMovie(id)
}