package com.jianastrero.yelpr.extension

import android.widget.ImageView
import com.jianastrero.yelpr.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun String?.into(imageView: ImageView) {
    CoroutineScope(Dispatchers.Main).launch {
        if (this@into.isNullOrEmpty()) {
            Picasso.get()
                .load(R.drawable.ic_broken_image)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .fit()
                .into(imageView)
        } else {
            Picasso.get()
                .load(this@into)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_broken_image)
                .centerCrop()
                .fit()
                .into(imageView)
        }
    }
}