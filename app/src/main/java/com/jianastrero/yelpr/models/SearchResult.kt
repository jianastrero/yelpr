package com.jianastrero.yelpr.models

data class SearchResult(
    var businesses: List<Businesse>,
    var region: Region,
    var total: Int
)