package com.example.moviesapplication.ui.activities

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.FragmentDateFilterBinding
import com.example.moviesapplication.utils.Constants
import com.example.moviesapplication.utils.getCalenderDate
import com.example.moviesapplication.utils.getDateFormat
import com.example.moviesapplication.utils.showToastAlert
import java.util.*


class MovieFilterActivity : AppCompatActivity() {

    private lateinit var binding: FragmentDateFilterBinding
    private var minDate: String? = null
    private var maxDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_date_filter)
        getIntentValues()
        initUI()
    }

    private fun initUI() {
        if (!TextUtils.isEmpty(minDate) && !TextUtils.isEmpty(maxDate)) {
            var date = getCalenderDate(minDate.orEmpty())
            binding.datePickerMin.updateDate(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
            )
            date = getCalenderDate(maxDate.orEmpty())
            binding.datePickerMax.updateDate(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
            )
        }
        binding.button.setOnClickListener {
            onApplyFilterClicked()
        }
    }

    private fun getIntentValues() {
        minDate = intent.getStringExtra(Constants.MINDATE)
        maxDate = intent.getStringExtra(Constants.MAXDATE)
    }

    private fun onApplyFilterClicked() {
        val minDay = binding.datePickerMin.dayOfMonth
        val maxDay = binding.datePickerMax.dayOfMonth
        val minMonth = binding.datePickerMin.month + 1
        val maxMonth = binding.datePickerMax.month + 1
        val minYear = binding.datePickerMin.year
        val maxYear = binding.datePickerMax.year

        val dateMin = getDateFormat(minYear, minMonth, minDay)
        val dateMax = getDateFormat(maxYear, maxMonth, maxDay)

        if (dateMin.before(dateMax)) {
            val intent = Intent()
            intent.putExtra(Constants.MINDATE, getString(R.string.release_date, minYear, minMonth, minDay))
            intent.putExtra(Constants.MAXDATE, getString(R.string.release_date, maxYear, maxMonth, maxDay))
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            showToastAlert(applicationContext, getString(R.string.wrong_filter_selected))
        }
    }

}