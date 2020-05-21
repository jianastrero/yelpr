package com.jianastrero.yelpr.model

import androidx.room.Embedded

data class Region(
    @Embedded(prefix = "center_")
    var center: Center
)