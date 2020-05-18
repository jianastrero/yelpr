package com.jianastrero.yelpr.extensions

import android.util.Log

fun String?.log(tag: String = "JIAN") = Log.d(tag, this.toString())