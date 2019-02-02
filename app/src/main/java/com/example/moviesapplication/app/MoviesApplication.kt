package com.example.moviesapplication.app

import android.app.Application
import com.example.moviesapplication.R

class MoviesApplication : Application() {

    companion object {
        lateinit var instance: MoviesApplication
            private set
    }

    lateinit var MoviesDbApiKey: String

    override fun onCreate() {
        super.onCreate()
        instance = this
        MoviesDbApiKey = resources.getString(R.string.movies_db_api_key)
    }
}
