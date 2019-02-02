package com.example.moviesapplication.network.service

import com.example.moviesapplication.network.models.MovieDetailResponseData
import com.example.moviesapplication.network.models.MoviesListResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("$MOVIE/$POPULAR")
    fun getNowPlayingMovies(@Query(API_KEY) apiKey: String, @Query(PAGE) pageNo: Int, @Query(LANGUAGE) language: String): Call<MoviesListResponseData>

    @GET("$MOVIE/{$MOVIE_ID}")
    fun getMovieDetail(@Path(MOVIE_ID) movieId: Int, @Query(API_KEY) apiKey: String): Call<MovieDetailResponseData>

    @GET("$DISCOVER/$MOVIE")
    fun getFilteredMovies(
        @Query(API_KEY) apiKey: String, @Query(PAGE) pageNo: Int, @Query(LANGUAGE) language: String, @Query(
            MIN_RELEASE_DATE
        ) minDate: String, @Query(MAX_RELEASE_DATE) maxDate: String
    ): Call<MoviesListResponseData>

}
