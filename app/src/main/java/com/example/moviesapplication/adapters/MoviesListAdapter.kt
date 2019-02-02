package com.example.moviesapplication.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapplication.R
import com.example.moviesapplication.network.models.MoviesListItemResponseData
import com.example.moviesapplication.utils.setImageUsingPicasso
import kotlinx.android.synthetic.main.item_movies.view.*

class MoviesListAdapter(
    private val context: Context,
    private val moviesList: ArrayList<MoviesListItemResponseData>,
    private val openDetailFunction: (movieDetailId: Int) -> Unit
) : RecyclerView.Adapter<MoviesListAdapter.NowPlayingMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NowPlayingMovieViewHolder = NowPlayingMovieViewHolder(
        LayoutInflater.from(context)
            .inflate(R.layout.item_movies, parent, false)
    )


    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        val item = moviesList[position]
        val itemView = holder?.itemView ?: return

        itemView.ItemTitle.text = item.movieTitle
        itemView.releaseDate.text = context.getString(R.string.release_date_text, item.releaseDate)
        setImageUsingPicasso(context, item.posterPath, itemView.ItemPosterImage)
        itemView.setOnClickListener { openDetailFunction(item.movieId) }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateAdapter(newMoviesData: List<MoviesListItemResponseData>) {
        val start = moviesList.size
        moviesList.addAll(newMoviesData)
        notifyItemRangeInserted(start, newMoviesData.size)
    }

    fun clearAddNew(newMoviesData: List<MoviesListItemResponseData>) {
        moviesList.clear()
        moviesList.addAll(newMoviesData)
        notifyDataSetChanged()
    }


    class NowPlayingMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}