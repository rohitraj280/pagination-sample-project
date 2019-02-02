package com.example.moviesapplication.ui.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviesapplication.R
import com.example.moviesapplication.adapters.MoviesListAdapter
import com.example.moviesapplication.databinding.FragmentMovieListBinding
import com.example.moviesapplication.listeners.MovieItemClickListener
import com.example.moviesapplication.listeners.PaginationScrollListener
import com.example.moviesapplication.network.models.MoviesListItemResponseData
import com.example.moviesapplication.network.models.MoviesListResponseData
import com.example.moviesapplication.ui.activities.MovieFilterActivity
import com.example.moviesapplication.utils.Constants
import com.example.moviesapplication.utils.LinearListSpacesItemDecoration
import com.example.moviesapplication.viewmodel.MoviesViewModel

class MovieListFragment : BaseFragment() {

    private lateinit var moviesResponseData: MoviesListResponseData
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var callback: MovieItemClickListener
    private lateinit var paginationScrollListener: PaginationScrollListener
    private var moviesListAdapter: MoviesListAdapter? = null
    private var minDate: String = ""
    private var maxDate: String = ""
    private val openDetail: (Int) -> Unit =
        { movieDetailId ->
            callback.onMovieItemClick(movieDetailId)
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        initUI()
        initializeObservers()
        fetchMovieList()
        return binding.root
    }


    private fun initUI() {
        moviesListAdapter = MoviesListAdapter(requireContext(), ArrayList<MoviesListItemResponseData>(), openDetail)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.generic_14dp)
        val itemDecoration = LinearListSpacesItemDecoration(spacingInPixels)
        val linearLayoutManager = LinearLayoutManager(activity)
        paginationScrollListener = object : PaginationScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (!TextUtils.isEmpty(minDate) && !TextUtils.isEmpty(maxDate)) moviesViewModel.getFilteredMovies(
                    page,
                    minDate,
                    maxDate
                )
                else moviesViewModel.getNowPlayingMovies(page)
            }
        }
        binding.nowPlayingMoviesRecyclerView.addOnScrollListener(paginationScrollListener)
        binding.nowPlayingMoviesRecyclerView.apply {
            addItemDecoration(itemDecoration)
            layoutManager = linearLayoutManager
            adapter = moviesListAdapter
        }
        binding.fab.setOnClickListener {
            openMovieFilterActivity()
        }

    }

    private fun openMovieFilterActivity() {
        val intent = Intent(context, MovieFilterActivity::class.java)
        intent.putExtra(Constants.MINDATE, minDate)
        intent.putExtra(Constants.MAXDATE, maxDate)
        this@MovieListFragment.startActivityForResult(intent, Constants.REQUEST_CODE_FILTER)
    }

    private fun initializeObservers() {
        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        moviesViewModel.movieListData.observe(this, Observer { response ->
            dismissProgressDialog()
            response?.let {
                updateMovieList(it)
            } ?: run {
                Toast.makeText(context, context?.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchMovieList() {
        showProgressDialog()
        moviesViewModel.getNowPlayingMovies(1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_FILTER && resultCode == Activity.RESULT_OK) {
            minDate = data?.extras?.getString(Constants.MINDATE).orEmpty()
            maxDate = data?.extras?.getString(Constants.MAXDATE).orEmpty()
            showProgressDialog()
            moviesViewModel.getFilteredMovies(1, minDate, maxDate)
        }
    }

    fun updateMovieList(movies: MoviesListResponseData) {
        moviesResponseData = movies
        if (movies.pageNumber == movies.totalNumberOfPages) paginationScrollListener.setHasMore(false)
        else paginationScrollListener.setHasMore(true)

        if (movies.pageNumber == 1) {
            paginationScrollListener.resetState()
            moviesListAdapter?.clearAddNew(movies.results)
        } else moviesListAdapter?.updateAdapter(movies.results)
    }

    companion object {
        fun newInstance(movieClickListener: MovieItemClickListener): MovieListFragment {
            val fragment = MovieListFragment()
            fragment.callback = movieClickListener
            return fragment
        }
    }

}