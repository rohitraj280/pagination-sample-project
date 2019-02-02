package com.example.moviesapplication.network.service

// Endpoint parts
const val TV = "tv"
const val MOVIE = "movie"
const val NOW_PLAYING = "now_playing"
const val POPULAR = "popular"
const val SEARCH = "search"
const val MULTI = "multi"
const val DISCOVER = "discover"

// Path parameters
const val TV_ID = "tv_id"
const val MOVIE_ID = "movie_id"

// Query string
const val API_KEY = "api_key"
const val QUERY = "query"
const val PAGE = "page"
const val LANGUAGE = "language"
const val MIN_RELEASE_DATE = "primary_release_date.gte"
const val MAX_RELEASE_DATE = "primary_release_date.lte"

const val API_VERSION = "3"
const val MOVIES_API_ENDPOINT = "https://api.themoviedb.org/$API_VERSION/"
const val IMAGES_API_ENDPOINT = "https://image.tmdb.org/t/p/w500"

