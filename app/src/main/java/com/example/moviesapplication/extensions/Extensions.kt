package com.example.moviesapplication.extensions


fun Int?.orZero() = this ?: 0
fun Boolean?.orFalse() = this ?: false