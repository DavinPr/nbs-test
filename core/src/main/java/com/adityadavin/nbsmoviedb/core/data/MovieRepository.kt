package com.adityadavin.nbsmoviedb.core.data

import android.annotation.SuppressLint
import android.util.Log
import com.adityadavin.nbsmoviedb.core.data.source.local.LocalDataSource
import com.adityadavin.nbsmoviedb.core.data.source.remote.RemoteDataSource
import com.adityadavin.nbsmoviedb.core.data.source.remote.network.ApiResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.MovieResultResponse
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
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IMovieRepository {
    override fun getBannerMovies(): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResultResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getMoviesBanner().map { list -> list.map { it.toDomain() } }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<MovieResultResponse>>> =
                remoteDataSource.getBannerMovies()

            override fun saveCallResult(data: List<MovieResultResponse>) {
                val dataList = data.map { it.toBannerEntity() }.take(3)
                localDataSource.insertMoviesBanner(dataList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

            override fun deleteLastItem(): Completable = localDataSource.deleteAllMoviesBanner()

        }.asFlowable()

    override fun getPopularMovies(): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResultResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getMoviesPopular().map { list -> list.map { it.toDomain() } }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<MovieResultResponse>>> =
                remoteDataSource.getPopularMovies()

            override fun saveCallResult(data: List<MovieResultResponse>) {
                val dataList = data.map { it.toPopularEntity() }.take(10)
                localDataSource.insertMoviesPopular(dataList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

            override fun deleteLastItem(): Completable = localDataSource.deleteAllMoviesPopular()

        }.asFlowable()

    override fun getComingSoonMovies(year: Int): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResultResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getMoviesComingSoon()
                    .map { list -> list.map { it.toDomain() } }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<MovieResultResponse>>> =
                remoteDataSource.getComingSoonMovies(year)

            override fun saveCallResult(data: List<MovieResultResponse>) {
                val dataList = data.map { it.toComingSoonEntity() }.take(10)
                localDataSource.insertMoviesComingSoon(dataList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

            override fun deleteLastItem(): Completable = localDataSource.deleteAllMoviesComingSoon()

        }.asFlowable()

    override fun getPopularMoviesOnYear(year: Int): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResultResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getMoviesPopularOnYear()
                    .map { list -> list.map { it.toDomain() } }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<MovieResultResponse>>> =
                remoteDataSource.getPopularMoviesOnYear(year)

            override fun saveCallResult(data: List<MovieResultResponse>) {
                val dataList = data.map { it.toPopularOnYearEntity() }
                localDataSource.insertMoviesPopularOnYear(dataList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

            override fun deleteLastItem(): Completable =
                localDataSource.deleteAllMoviesPopularOnYear()

        }.asFlowable()

    override fun getFilteredPopularMovies(title: String): Flowable<Resource<List<Movie>>> {
        val result = PublishSubject.create<Resource<List<Movie>>>()
        val mCompositeDisposable = CompositeDisposable()
        val disposable = localDataSource.getFilteredMoviePopular(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { mCompositeDisposable.dispose() }
            .subscribe { list ->
                if (list.isNullOrEmpty()) {
                    result.onNext(Resource.Error("Item not found", null))
                } else {
                    result.onNext(Resource.Success(list.map { it.toDomain() }))
                }
            }
        mCompositeDisposable.add(disposable)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getAllLocalPopularMovies(): Flowable<Resource<List<Movie>>> {
        val result = PublishSubject.create<Resource<List<Movie>>>()
        val mCompositeDisposable = CompositeDisposable()
        val disposable = localDataSource.getMoviesPopularOnYear()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { mCompositeDisposable.dispose() }
            .subscribe { list ->
                if (list.isNullOrEmpty()) {
                    result.onNext(Resource.Error("Item not found", null))
                } else {
                    result.onNext(Resource.Success(list.map { it.toDomain() }))
                }
            }
        mCompositeDisposable.add(disposable)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getDetailMovie(id: Int): Flowable<Resource<DetailMovie>> {
        val result = PublishSubject.create<Resource<DetailMovie>>()
        val mCompositeDisposable = CompositeDisposable()
        result.onNext(Resource.Loading(null))
        val disposable = Flowable.zip(
            remoteDataSource.getDetailMovie(id).subscribeOn(Schedulers.io()),
            remoteDataSource.getCastMovie(id).subscribeOn(Schedulers.io()),
            { t1, t2 ->
                when (t1) {
                    is ApiResponse.Success -> {
                        val data = t1.data.toDomain()
                        when (t2) {
                            is ApiResponse.Success -> {
                                data.message = "All Response Success"
                                data.isCombineSuccess = true
                                data.cast = t2.data.map { it.toDomain() }.take(10)
                            }
                            is ApiResponse.Empty -> {
                                data.message = "Cast Response Empty"
                                data.isCombineSuccess = false
                                data.cast = listOf()
                            }
                            is ApiResponse.Error -> {
                                data.isCombineSuccess = false
                                data.message = "All Response Success"
                            }
                        }
                        Resource.Success(data)
                    }
                    is ApiResponse.Empty -> Resource.Success(DetailMovie())
                    is ApiResponse.Error -> Resource.Error(t1.message, DetailMovie())
                }
            }
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { mCompositeDisposable.dispose() }
            .subscribe { result.onNext(it) }
        mCompositeDisposable.add(disposable)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun getMovieFavorite(): Flowable<Resource<List<FavoriteMovie>>> {
        val result = PublishSubject.create<Resource<List<FavoriteMovie>>>()
        val mCompositeDisposable = CompositeDisposable()
        result.onNext(Resource.Loading(null))
        val response = localDataSource.getMoviesFavorite()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }
            .subscribe { data ->
                if (data.isEmpty()) result.onNext(Resource.Error("Data not found"))
                else result.onNext(Resource.Success(data.map { it.toDomain() }))
            }
        mCompositeDisposable.add(response)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun insertFavorite(movie: FavoriteMovie): Single<Boolean> {
        val result = SingleSubject.create<Boolean>()
        val mCompositeDisposable = CompositeDisposable()
        val insert = localDataSource.insertMovieFavorite(movie.toEntity())
        val disposable = insert.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { mCompositeDisposable.dispose() }
            .subscribe({
                insert.unsubscribeOn(Schedulers.io())
                result.onSuccess(true)
            }, { error ->
                result.onSuccess(false)
                Log.d("Insert Favorite", error.message.toString())
            })
        mCompositeDisposable.add(disposable)
        return result
    }

    override fun deleteFavorite(movie: FavoriteMovie): Single<Boolean> {
        TODO("Not yet implemented")
    }
}