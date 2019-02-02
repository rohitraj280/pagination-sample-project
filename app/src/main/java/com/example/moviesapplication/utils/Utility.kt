package com.example.moviesapplication.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.example.moviesapplication.network.service.IMAGES_API_ENDPOINT
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

fun showToastAlert(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getDateFormat(day: Int, month: Int, year: Int): Date {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val strDate: String = year.toString() + "-" + month.toString() + "-" + day.toString()
    val date = sdf.parse(strDate)
    return date
}


fun getCalenderDate(s: String): Calendar {

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(s)
    val cal = Calendar.getInstance()
    cal.time = date
    return cal
}

fun setImageUsingPicasso(context: Context, partialPath: String?, imageView: ImageView) =
    Picasso.with(context).load(getFullImageUrl(partialPath.orEmpty())).into(imageView)

// Dialog Utils

fun getFullImageUrl(imageName: String): String = "${IMAGES_API_ENDPOINT}$imageName"