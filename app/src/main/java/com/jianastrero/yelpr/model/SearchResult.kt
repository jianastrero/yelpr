package com.jianastrero.yelpr.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "search_results")
data class SearchResult @JvmOverloads constructor(
    @Ignore
    var businesses: List<Business>,
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var region: Region,
    var total: Int,
    var term: String? = null,
    var location: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null
)