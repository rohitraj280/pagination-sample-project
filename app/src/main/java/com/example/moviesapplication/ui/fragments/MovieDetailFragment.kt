package com.example.moviesapplication.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.FragmentMovieDetailBinding
import com.example.moviesapplication.extensions.orZero
import com.example.moviesapplication.network.models.MovieDetailResponseData
import com.example.moviesapplication.utils.Constants.MOVIE_DETAIL_ID
import com.example.moviesapplication.utils.setImageUsingPicasso
import com.example.moviesapplication.utils.showToastAlert
import com.example.moviesapplication.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : BaseFragment() {

    private lateinit var movieDetail: MovieDetailResponseData
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: FragmentMovieDetailBinding
    private val movieDetailId: Int by lazy {
        arguments?.getInt(MOVIE_DETAIL_ID).orZero()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        initializeObserver()
        fetchMovieDetail()
        return binding.root
    }

    private fun initializeObserver() {
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        moviesViewModel.movieDetailData.observe(this, Observer { response ->
            response?.let {
                populateMovieDetail(it)
            } ?: run {
                onFetchFailMovieDetail()
            }
        })
    }

    private fun fetchMovieDetail() {
        showProgressDialog()
        moviesViewModel.getMovieDetail(movieDetailId.orZero())
    }

    private fun populateMovieDetail(movieDetail: MovieDetailResponseData) {
        dismissProgressDialog()
        this.movieDetail = movieDetail
        movieDetailDescriptionTextView.text = movieDetail.overview
        movieDetailTitleTextView.text = movieDetail.title
        movieDetailRatingTextView.text = getString(R.string.rating, movieDetail.voteAverage.toString())
        setImageUsingPicasso(requireContext(), movieDetail.posterPath, movieDetailPosterImageView)
    }

    private fun onFetchFailMovieDetail() {
        showToastAlert(requireContext(), getString(R.string.movie_detail_fetch_fail))
    }

    companion object {
        fun newInstance(movieDetailId: Int): MovieDetailFragment {
            val args = Bundle().apply {
                putInt(MOVIE_DETAIL_ID, movieDetailId)
            }
            return MovieDetailFragment().apply { arguments = args }
        }
    }
}