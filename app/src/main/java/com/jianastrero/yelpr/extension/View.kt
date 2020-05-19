package com.jianastrero.yelpr.extension

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("jianastrero:visible")
fun View.bindingVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}