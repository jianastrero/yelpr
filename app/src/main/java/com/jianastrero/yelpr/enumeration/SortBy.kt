package com.jianastrero.yelpr.enumeration

enum class SortBy {
    NAME_ASC,
    NAME_DESC,
    PRICE_ASC,
    PRICE_DESC,
    DISTANCE_ASC,
    DISTANCE_DESC;

    companion object {

        fun getAll(keyword: String) =
            values().filter { it.name.contains(keyword, true) }
    }
}