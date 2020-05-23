package com.jianastrero.yelpr.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.lang.Exception

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
    var hours: List<Hour>?,
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
    var price: String?,
    var rating: Double,
    @SerializedName("review_count")
    var reviewCount: Int,
    var url: String
) {

    fun categoriesString() = try {
        categories.joinToString { it.title }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }

    fun schedule(): String = try {
        val hours = this.hours

        if (hours != null) {
            val distinctDays = mutableSetOf<Int>()
            val opens = mutableListOf<Open>()

            hours.forEach { hour ->
                hour.openList.forEach {
                    distinctDays.add(it.day)
                }
                opens.addAll(hour.openList)
            }

            val days = distinctDays.toMutableList().sorted()
            var string = ""

            days.forEach { day ->
                val x = opens.filter { it.day == day }
                val start = x.minBy { it.start }?.start ?: ""
                val end = x.maxBy { it.end }?.end ?: ""

                val dayString = when (day) {
                    0 -> "Monday"
                    1 -> "Tuesday"
                    2 -> "Wednesday"
                    3 -> "Thursday"
                    4 -> "Friday"
                    5 -> "Saturday"
                    else -> "Sunday"
                }

                string += "$dayString from ${start.toTime} to ${end.toTime}\n"
            }

            string.substring(0, string.length - 1)
        } else {
            "No Schedule Available"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "No Schedule Available"
    }

    fun phoneText(): String {
        return if (displayPhone.trim().isEmpty()) {
            "None"
        } else {
            displayPhone
        }
    }

    private val String.toTime: String
        get() {
            var hour = substring(0 until 2).toInt()
            val minutes = substring(2 until 4)

            val apm = if (hour > 11) {
                "PM"
            } else {
                "AM"
            }

            if (hour > 12) {
                hour -= 12
            }

            return "$hour:$minutes $apm"
        }
}