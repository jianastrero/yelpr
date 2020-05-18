package com.jianastrero.yelpr.model

data class SearchResult(
    var businesses: List<Businesse>,
    var region: Region,
    var total: Int
)