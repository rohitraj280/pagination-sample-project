package com.example.moviesapplication.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/*
* Item Decoration
* */


class LinearListSpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if (parent.getChildLayoutPosition(view) != 0) {
            outRect.top = space
        }
    }
}