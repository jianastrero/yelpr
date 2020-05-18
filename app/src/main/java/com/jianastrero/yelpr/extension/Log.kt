package com.jianastrero.yelpr.extension

import android.util.Log

fun String?.log(tag: String = "JIAN") = Log.d(tag, this.toString())