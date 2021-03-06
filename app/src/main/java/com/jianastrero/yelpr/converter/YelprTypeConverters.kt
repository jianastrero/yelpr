package com.jianastrero.yelpr.converter

import androidx.room.TypeConverter
import com.jianastrero.yelpr.model.Category
import com.jianastrero.yelpr.model.Hour
import com.jianastrero.yelpr.model.Region
import com.jianastrero.yelpr.singleton.fromJson
import com.jianastrero.yelpr.singleton.toJson

class YelprTypeConverters {

    @TypeConverter
    fun toCategories(json: String?): List<Category> = (json ?: "[]").fromJson()

    @TypeConverter
    fun fromCategories(list: List<Category>?): String = list?.toJson() ?: "[]"

    @TypeConverter
    fun toHours(json: String?): List<Hour> = (json ?: "[]").fromJson()

    @TypeConverter
    fun fromHours(list: List<Hour>?): String = list?.toJson() ?: "[]"

    @TypeConverter
    fun toStrings(json: String?): List<String> = (json ?: "[]").fromJson()

    @TypeConverter
    fun fromStrings(list: List<String>?): String = list?.toJson() ?: "[]"
}