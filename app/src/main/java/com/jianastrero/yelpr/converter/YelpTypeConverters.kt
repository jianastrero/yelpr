package com.jianastrero.yelpr.converter

import com.jianastrero.yelpr.model.Category
import com.jianastrero.yelpr.singleton.fromJson
import com.jianastrero.yelpr.singleton.toJson

object YelpTypeConverters {

    fun toCategories(json: String): List<Category> = json.fromJson()

    fun fromCategories(list: List<Category>): String = list.toJson()

    fun toStrings(json: String): List<String> = json.fromJson()

    fun fromStrings(list: List<String>): String = list.toJson()
}