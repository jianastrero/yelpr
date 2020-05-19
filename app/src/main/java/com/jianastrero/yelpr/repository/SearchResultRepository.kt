package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.YelprDatabase
import com.jianastrero.yelpr.dao.SearchResultDao
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.repository.base.CrudRepository

class SearchResultRepository private constructor() :
    CrudRepository<SearchResult, SearchResultDao>(YelprDatabase.getInstance().searchResultDao()) {

    companion object {

        private lateinit var instance: SearchResultRepository

        fun getInstance(): SearchResultRepository {

            if (!::instance.isInitialized) {
                instance = SearchResultRepository()
            }

            return instance
        }
    }

    private val businessRepository = BusinessRepository.getInstance()

    override suspend fun insert(vararg items: SearchResult) {
        super.insert(*items)

        items.forEach { item ->
            item.businesses.forEach {
                it.searchResultId = item.localId
            }
            businessRepository.insert(*item.businesses.toTypedArray())
        }
    }

    suspend fun get(latitude: Double, longitude: Double, term: String): SearchResult? =
        dao.get(latitude, longitude, term)
}