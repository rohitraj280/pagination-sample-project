package com.example.moviesapplication.listeners

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager


abstract class PaginationScrollListener : RecyclerView.OnScrollListener {
    private val mLayoutManager: RecyclerView.LayoutManager
    private val startingPageIndex = 1
    private var visibleThreshold = 5
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private var hasMore: Boolean = false
    private var reverLay: Boolean = false // reverseLay is for reversing the direction of layout

    constructor(layoutManager: LinearLayoutManager) {
        this.mLayoutManager = layoutManager
        reverLay = layoutManager.reverseLayout
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        lastVisibleItemPositions.indices.forEach { i ->
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView == null) return
        if (mLayoutManager.canScrollVertically() && (!reverLay && dy >= 0 || reverLay && dy <= 0)) {
            handlePagination(recyclerView)
        } else if (mLayoutManager.canScrollHorizontally() && (!reverLay && dx > 0 || reverLay && dx < 0)) {
            handlePagination(recyclerView)
        } else if (reverLay && dy < 0) {
            handlePagination(recyclerView)
        }
    }

    private fun handlePagination(view: RecyclerView) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = mLayoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> lastVisibleItemPosition =
                    mLayoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> lastVisibleItemPosition =
                    mLayoutManager.findLastVisibleItemPosition()
        }

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (hasMore && !loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.loading = true
    }


    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)

}