package com.jianastrero.yelpr.model

data class SearchResult(
    var businesses: List<Business>,
    var region: Region,
    var total: Int
)