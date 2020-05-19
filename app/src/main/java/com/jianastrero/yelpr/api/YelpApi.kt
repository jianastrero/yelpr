package com.jianastrero.yelpr.api

import com.jianastrero.yelpr.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpApi {

    @GET("businesses/search")
    suspend fun search(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("term") term: String
    ): Response<SearchResult>
}