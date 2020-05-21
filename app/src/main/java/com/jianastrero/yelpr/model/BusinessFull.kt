package com.jianastrero.yelpr.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "business_fulls",
    indices = [
        Index("id")
    ]
)
data class BusinessFull(
    var alias: String,
    var categories: List<Category>,
    @Embedded(prefix = "coordinates_")
    var coordinates: Coordinates,
    @SerializedName("display_phone")
    var displayPhone: String,
    var hours: List<Hour>,
    @PrimaryKey
    var id: String,
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("is_claimed")
    var isClaimed: Boolean,
    @SerializedName("is_closed")
    var isClosed: Boolean,
    @Embedded(prefix = "location_")
    var location: Location,
    var name: String,
    var phone: String,
    var photos: List<String>,
    var price: String,
    var rating: Double,
    @SerializedName("review_count")
    var reviewCount: Int,
    var url: String
) {

    fun categoriesString() = categories.joinToString { it.title }
}