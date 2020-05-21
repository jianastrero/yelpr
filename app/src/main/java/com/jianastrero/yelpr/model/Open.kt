package com.jianastrero.yelpr.model

import com.google.gson.annotations.SerializedName

data class Open(
    var day: Int,
    var end: String,
    @SerializedName("is_overnight")
    var isOvernight: Boolean,
    var start: String
)