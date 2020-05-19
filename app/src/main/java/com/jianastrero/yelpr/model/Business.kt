package com.jianastrero.yelpr.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "businesses"
)
data class Business @JvmOverloads constructor(
    var alias: String,
    var categories: List<Category>,
    @Embedded(prefix = "coordinates")
    var coordinates: Coordinates,
    var distance: Double,
    var id: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("is_closed")
    var isClosed: Boolean,
    @Embedded(prefix = "location_")
    var location: Location,
    var name: String,
    var phone: String,
    var price: String,
    var rating: Int,
    @SerializedName("review_count")
    var reviewCount: Int,
    var transactions: List<String>,
    var url: String,
    @PrimaryKey(autoGenerate = true)
    var localId: Int = 0,
    var searchResultId: Int = 0
)