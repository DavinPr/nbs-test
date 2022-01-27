package com.adityadavin.nbsmoviedb.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.adityadavin.nbsmoviedb.core.BuildConfig
import com.adityadavin.nbsmoviedb.core.data.source.remote.network.ApiResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.network.ApiService
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.CastItemResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.DetailMovieResponse
import com.adityadavin.nbsmoviedb.core.data.source.remote.response.MovieResultResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
class RemoteDataSource(private val apiService: ApiService) {

    fun getBannerMovies(): Flowable<ApiResponse<List<MovieResultResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResultResponse>>>()

        val client = apiService.getBannerMovies(BuildConfig.API_KEY)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getPopularMovies(): Flowable<ApiResponse<List<MovieResultResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResultResponse>>>()

        val client = apiService.getPopularMovies(BuildConfig.API_KEY)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getComingSoonMovies(year: Int): Flowable<ApiResponse<List<MovieResultResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResultResponse>>>()

        val client = apiService.getComingSoonMovies(BuildConfig.API_KEY, year = year)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getPopularMoviesOnYear(year: Int): Flowable<ApiResponse<List<MovieResultResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResultResponse>>>()

        val client = apiService.getPopularMoviesOnYear(BuildConfig.API_KEY, year = year)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMovie(id: Int): Flowable<ApiResponse<DetailMovieResponse>> {
        val resultData = PublishSubject.create<ApiResponse<DetailMovieResponse>>()
        val client = apiService.getDetailMovie(id, BuildConfig.API_KEY)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getCastMovie(id: Int): Flowable<ApiResponse<List<CastItemResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<CastItemResponse>>>()
        val client = apiService.getMovieCast(id, BuildConfig.API_KEY)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.cast
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

}