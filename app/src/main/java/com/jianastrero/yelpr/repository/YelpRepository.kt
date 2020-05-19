package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.enumeration.API
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.singleton.RetrofitSingleton
import java.lang.Exception

class YelpRepository {

    private val api = RetrofitSingleton.yelpApi()
    private val searchResultRepository = SearchResultRepository.getInstance()

    suspend fun search(
        latitude: Double,
        longitude: Double,
        term: String = ""
    ): Pair<API, SearchResult?> = try {
        val response = api.search(latitude, longitude, term)
        val code = response.code()

        if (response.isSuccessful) {
            val result = response.body()

            // Cache results
            if (result != null) {
                result.latitude = latitude
                result.longitude = longitude
                result.term = term

                searchResultRepository.insert(result)
            }

            Pair(API.OK, result)
        } else {
            when (code) {
                404 -> Pair(API.NOT_FOUND, null)
                422 -> Pair(API.INVALID_INPUT, null)
                in 400 until 500 -> Pair(API.GENERAL_INPUT_ERROR, null)
                in 500 until 600 -> Pair(API.GENERAL_SERVER_ERROR, null)
                else -> Pair(
                    API.NO_INTERNET_CONNECTION,
                    searchLocally(latitude, longitude, term)
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(API.NO_INTERNET_CONNECTION, searchLocally(latitude, longitude, term))
    }

    private suspend fun searchLocally(
        latitude: Double,
        longitude: Double,
        term: String = ""
    ): SearchResult? =
        searchResultRepository.get(latitude, longitude, term)
}