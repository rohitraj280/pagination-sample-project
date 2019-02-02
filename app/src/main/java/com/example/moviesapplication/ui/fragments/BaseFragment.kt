package com.example.moviesapplication.ui.fragments

import android.support.v4.app.Fragment
import com.example.moviesapplication.extensions.orFalse
import com.example.moviesapplication.ui.dialogs.CustomProgressDialog

abstract class BaseFragment : Fragment() {

    var dialogFragment: CustomProgressDialog? = null

    fun showProgressDialog() {
        if (dialogFragment != null && dialogFragment?.isVisible.orFalse()) return
        dialogFragment = CustomProgressDialog()
        dialogFragment?.isCancelable = false
        dialogFragment?.show(fragmentManager, "")
    }

    fun dismissProgressDialog() {
        if (dialogFragment?.isVisible.orFalse()) {
            dialogFragment?.dismiss()
        }
    }

}