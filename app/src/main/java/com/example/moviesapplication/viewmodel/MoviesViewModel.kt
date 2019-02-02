package com.example.moviesapplication.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.moviesapplication.network.datasource.MoviesDataSourceRespository
import com.example.moviesapplication.network.models.MovieDetailResponseData
import com.example.moviesapplication.network.models.MoviesListResponseData

class MoviesViewModel : ViewModel() {

    var movieListData = MutableLiveData<MoviesListResponseData>()
    var movieDetailData = MutableLiveData<MovieDetailResponseData>()

    fun getNowPlayingMovies(pageNo: Int) {
        MoviesDataSourceRespository.instance.getNowPlayingMovies(movieListData, pageNo)
    }

    fun getFilteredMovies(pageNo: Int, minDate: String, maxDate: String) {
        MoviesDataSourceRespository.instance.getFilteredMovies(movieListData, pageNo, minDate, maxDate)
    }

    fun getMovieDetail(movieID: Int) {
        MoviesDataSourceRespository.instance.getMovieDetail(movieID, movieDetailData)
    }


}
