package com.example.moviesapplication.network.datasource

import android.arch.lifecycle.MutableLiveData
import com.example.moviesapplication.network.models.MovieDetailResponseData
import com.example.moviesapplication.network.models.MoviesListResponseData
import com.example.moviesapplication.network.service.MoviesApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesDataSourceRespository : RemoteDataSource() {

    private val service: MoviesApiService = retrofit.create(MoviesApiService::class.java)

    private object Holder {
        val INSTANCE = MoviesDataSourceRespository()
    }

    fun getNowPlayingMovies(data: MutableLiveData<MoviesListResponseData>, pageNo: Int) {
        service.getNowPlayingMovies(apiKey, pageNo, "en-US").enqueue(object : Callback<MoviesListResponseData> {
            override fun onResponse(call: Call<MoviesListResponseData>?, response: Response<MoviesListResponseData>?) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    data.value = responseBody
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<MoviesListResponseData>?, t: Throwable?) {
                data.value = null
            }
        })
    }

    fun getMovieDetail(movieId: Int, data: MutableLiveData<MovieDetailResponseData>) {
        service.getMovieDetail(movieId, apiKey).enqueue(object : Callback<MovieDetailResponseData> {
            override fun onResponse(
                call: Call<MovieDetailResponseData>?,
                response: Response<MovieDetailResponseData>?
            ) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    data.value = responseBody
                } else {
                    data.value = null
                }
            }

            override fun onFailure(call: Call<MovieDetailResponseData>?, t: Throwable?) {
                data.value = null
            }
        })
    }

    fun getFilteredMovies(
        data: MutableLiveData<MoviesListResponseData>,
        pageNo: Int,
        minDate: String,
        maxDate: String
    ) {
        service.getFilteredMovies(apiKey, pageNo, "en-US", minDate, maxDate)
            .enqueue(object : Callback<MoviesListResponseData> {
                override fun onResponse(
                    call: Call<MoviesListResponseData>?,
                    response: Response<MoviesListResponseData>?
                ) {
                    val responseBody = response?.body()
                    if (responseBody != null) {
                        data.value = responseBody
                    } else {
                        data.value = null
                    }
                }

                override fun onFailure(call: Call<MoviesListResponseData>?, t: Throwable?) {
                    data.value = null
                }
            })
    }


    companion object {
        val instance: MoviesDataSourceRespository by lazy { Holder.INSTANCE }
    }
}