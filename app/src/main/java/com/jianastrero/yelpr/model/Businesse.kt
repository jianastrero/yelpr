package com.jianastrero.yelpr.model

import com.google.gson.annotations.SerializedName

data class Businesse(
    var alias: String,
    var categories: List<Category>,
    var coordinates: Coordinates,
    var distance: Double,
    var id: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("is_closed")
    var isClosed: Boolean,
    var location: Location,
    var name: String,
    var phone: String,
    var price: String,
    var rating: Int,
    @SerializedName("review_count")
    var reviewCount: Int,
    var transactions: List<String>,
    var url: String
)