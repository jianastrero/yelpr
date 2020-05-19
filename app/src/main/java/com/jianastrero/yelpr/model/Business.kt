package com.jianastrero.yelpr.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "businesses",
    foreignKeys = [
        ForeignKey(
            entity = SearchResult::class,
            childColumns = ["searchResultId"],
            parentColumns = ["localId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("id", unique = true),
        Index("searchResultId")
    ]
)
data class Business @JvmOverloads constructor(
    var alias: String,
    var categories: List<Category>,
    @Embedded(prefix = "coordinates")
    var coordinates: Coordinates,
    var distance: Double,
    @PrimaryKey
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
    var rating: Double,
    @SerializedName("review_count")
    var reviewCount: Int,
    var transactions: List<String>,
    var url: String,
    var searchResultId: Int = 0
)