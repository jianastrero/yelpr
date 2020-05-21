package com.jianastrero.yelpr.model

import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("hours_type")
    var hoursType: String,
    @SerializedName("is_open_now")
    var isOpenNow: Boolean,
    @SerializedName("open")
    var openList: List<Open>
)