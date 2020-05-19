package com.jianastrero.yelpr.model

import androidx.room.Embedded

data class Region(
    @Embedded(prefix = "coordinates")
    var center: Center
)