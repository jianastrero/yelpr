package com.jianastrero.yelpr.models

import com.google.gson.annotations.SerializedName

data class Category(
    var alias: String,
    @SerializedName("country_blacklist")
    var countryBlacklist: List<String>,
    @SerializedName("country_whitelist")
    var countryWhitelist: List<String>,
    @SerializedName("parent_aliases")
    var parentAliases: List<String>,
    var title: String
)