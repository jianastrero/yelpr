package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.enumeration.ResponseCode
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.singleton.RetrofitSingleton
import java.lang.Exception

class YelpRepository {

    companion object {

        private lateinit var instance: YelpRepository

        fun getInstance(): YelpRepository {

            if (!::instance.isInitialized) {
                instance = YelpRepository()
            }

            return instance
        }
    }

    private val api = RetrofitSingleton.yelpApi()
    private val searchResultRepository = SearchResultRepository.getInstance()

    suspend fun search(
        latitude: Double,
        longitude: Double,
        term: String = ""
    ): Pair<ResponseCode, SearchResult?> = try {
        val response = if (term == "") {
            api.search(latitude, longitude)
        } else {
            api.search(latitude, longitude, term)
        }
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

            Pair(ResponseCode.OK, result)
        } else {
            when (code) {
                404 -> Pair(ResponseCode.NOT_FOUND, null)
                422 -> Pair(ResponseCode.INVALID_INPUT, null)
                in 400 until 500 -> Pair(ResponseCode.GENERAL_INPUT_ERROR, null)
                in 500 until 600 -> Pair(ResponseCode.GENERAL_SERVER_ERROR, null)
                else -> Pair(
                    ResponseCode.NO_INTERNET_CONNECTION,
                    searchLocally(latitude, longitude, term)
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(ResponseCode.NO_INTERNET_CONNECTION, searchLocally(latitude, longitude, term))
    }

    private suspend fun searchLocally(
        latitude: Double,
        longitude: Double,
        term: String = ""
    ): SearchResult? =
        searchResultRepository.getSuspended(latitude, longitude, term)
}