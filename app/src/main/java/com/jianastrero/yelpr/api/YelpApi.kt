package com.jianastrero.yelpr.api

import com.jianastrero.yelpr.models.CategoriesResult
import retrofit2.http.GET

interface YelpApi {

    @GET("categories")
    suspend fun categories(): CategoriesResult
}