package com.jianastrero.yelpr.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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

    override suspend fun insert(vararg items: SearchResult): List<Long> {
        items.forEach {
            dao.delete(it.latitude ?: 0.0, it.longitude ?: 0.0, it.term ?: "")
        }
        val ids = super.insert(*items)

        items.forEachIndexed { i, item ->
            item.businesses.forEach {
                it.searchResultId = ids[i].toInt()
            }
            businessRepository.insert(*item.businesses.toTypedArray())
        }

        return ids
    }

    fun get(latitude: Double, longitude: Double, term: String): LiveData<SearchResult?> =
        dao.get(latitude, longitude, term)
            .asLiveData()

    suspend fun getSuspended(latitude: Double, longitude: Double, term: String): SearchResult? =
        dao.getSuspended(latitude, longitude, term)
}