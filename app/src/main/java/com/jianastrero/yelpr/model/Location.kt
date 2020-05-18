package com.jianastrero.yelpr.model

import com.google.gson.annotations.SerializedName

data class Location(
    var address1: String,
    var address2: String,
    var address3: String,
    var city: String,
    var country: String,
    var state: String,
    @SerializedName("zip_code")
    var zipCode: String
)