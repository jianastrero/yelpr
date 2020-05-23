package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.enumeration.ResponseCode
import com.jianastrero.yelpr.model.BusinessFull
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
    private val businessFullRepository = BusinessFullRepository.getInstance()

    suspend fun search(
        latitude: Double,
        longitude: Double,
        term: String = ""
    ): Pair<ResponseCode, SearchResult?> = try {
        val response = if (term == "") {
            api.search(latitude, longitude, 50)
        } else {
            api.search(latitude, longitude, term, 50)
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

    suspend fun search(location: String): Pair<ResponseCode, SearchResult?> = try {
        val response = api.search(location, 50)
        val code = response.code()

        if (response.isSuccessful) {
            val result = response.body()

            // Cache results
            if (result != null) {
                result.latitude = result.region?.center?.latitude
                result.longitude = result.region?.center?.longitude
                result.term = location

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
                    searchLocally(location)
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(ResponseCode.NO_INTERNET_CONNECTION, searchLocally(location))
    }

    private suspend fun searchLocally(
        latitude: Double,
        longitude: Double,
        term: String = ""
    ): SearchResult? =
        searchResultRepository.getSuspended(latitude, longitude, term)
            ?: searchResultRepository.getLastSuspended()

    private suspend fun searchLocally(location: String): SearchResult? =
        searchResultRepository.getSuspended(location)

    suspend fun get(id: String): Pair<ResponseCode, BusinessFull?> = try {
        val response = api.get(id)

        val code = response.code()

        if (response.isSuccessful) {
            val result = response.body()

            // Cache results
            if (result != null) {
                businessFullRepository.insert(result)
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
                    businessFullRepository.getSuspended(id)
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Pair(ResponseCode.NO_INTERNET_CONNECTION, businessFullRepository.getSuspended(id))
    }
}