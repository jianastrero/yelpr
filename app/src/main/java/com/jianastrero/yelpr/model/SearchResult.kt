package com.jianastrero.yelpr.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_results",
    indices = [
        Index("term", "latitude", "longitude", unique = true)
    ]
)
data class SearchResult @JvmOverloads constructor(
    @Ignore
    var businesses: List<Business>,
    @PrimaryKey(autoGenerate = true)
    var localId: Int,
    var region: Region,
    var total: Int,
    var term: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null
)