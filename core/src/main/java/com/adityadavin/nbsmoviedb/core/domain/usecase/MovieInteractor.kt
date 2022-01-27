package com.adityadavin.nbsmoviedb.core.domain.usecase

import com.adityadavin.nbsmoviedb.core.data.Resource
import com.adityadavin.nbsmoviedb.core.domain.model.CategoryMovie
import com.adityadavin.nbsmoviedb.core.domain.model.DetailMovie
import com.adityadavin.nbsmoviedb.core.domain.model.FavoriteMovie
import com.adityadavin.nbsmoviedb.core.domain.model.Movie
import com.adityadavin.nbsmoviedb.core.domain.repository.IMovieRepository
import com.adityadavin.nbsmoviedb.core.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
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

    override fun getFilteredMovieFavorite(title: String): Flowable<Resource<List<FavoriteMovie>>> =
        repository.getFilteredMovieFavorite(title)

    override fun getAllLocalPopularMovies(): Flowable<Resource<List<Movie>>> =
        repository.getAllLocalPopularMovies()

    override fun getDetailMovie(id: Int): Flowable<Resource<DetailMovie>> =
        repository.getDetailMovie(id)

    override fun getMovieFavorite(): Flowable<Resource<List<FavoriteMovie>>> =
        repository.getMovieFavorite()

    override fun insertFavorite(movie: DetailMovie): Completable =
        repository.insertFavorite(movie.toFavoriteMovie())

    override fun deleteFavorite(movie: FavoriteMovie): Completable =
        repository.deleteFavorite(movie)

    override fun deleteFavoriteFromDetail(movie: DetailMovie): Completable =
        repository.deleteFavorite(movie.toFavoriteMovie())

    override fun isFavorite(id: Int): Single<Boolean> = repository.isFavorite(id)
}