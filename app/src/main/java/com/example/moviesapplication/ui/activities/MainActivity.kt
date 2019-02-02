package com.example.moviesapplication.ui.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.moviesapplication.R
import com.example.moviesapplication.listeners.MovieItemClickListener
import com.example.moviesapplication.ui.fragments.MovieDetailFragment
import com.example.moviesapplication.ui.fragments.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*

//Note - I have followed complete MVVM architecture for designing this app.


class MainActivity : AppCompatActivity(), MovieItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(MovieListFragment.newInstance(this))
        mainToolbar.title = getString(R.string.movies_info)
    }

    override fun onMovieItemClick(movieId: Int) {
        addFragment(MovieDetailFragment.newInstance(movieId))
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.mainFrameLayout, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count > 0) {
            if (count == 1) finish()
            else supportFragmentManager.popBackStack()
        } else super.onBackPressed()
    }
}
