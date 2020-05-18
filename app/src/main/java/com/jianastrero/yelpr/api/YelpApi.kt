package com.jianastrero.yelpr.api

import com.jianastrero.yelpr.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpApi {

    @GET("businesses/search")
    suspend fun search(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): SearchResult
}