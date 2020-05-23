package com.jianastrero.yelpr.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity?.hideKeyboard() {
    this?.apply {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?

        imm?.hideSoftInputFromWindow(
            (currentFocus ?: View(this)).windowToken,
            0
        )
    }
}

fun AppCompatActivity?.showKeyboard() {
    this?.apply {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
    }
}