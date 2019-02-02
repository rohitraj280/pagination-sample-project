package com.example.moviesapplication.network.models

import com.google.gson.annotations.SerializedName

data class MoviesListResponseData(
    @SerializedName("results") val results: List<MoviesListItemResponseData>,
    @SerializedName("page") val pageNumber: Int,
    @SerializedName("total_results") val totalNumberOfResults: Int,
    @SerializedName("dates") val dates: MoviesListDatesResponseData,
    @SerializedName("total_pages") val totalNumberOfPages: Int
)

data class MoviesListDatesResponseData(
    @SerializedName("maximum") val maximumDate: String,
    @SerializedName("minimum") val minimumDate: String
)

data class MoviesListItemResponseData(
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("id") val movieId: Int,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("title") val movieTitle: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("genre_ids") val genreIds: List<Int>
)