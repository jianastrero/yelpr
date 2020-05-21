package com.jianastrero.yelpr.repository

import androidx.lifecycle.asLiveData
import com.jianastrero.yelpr.YelprDatabase
import com.jianastrero.yelpr.dao.BusinessDao
import com.jianastrero.yelpr.dao.BusinessFullDao
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.model.BusinessFull
import com.jianastrero.yelpr.repository.base.CrudRepository

class BusinessFullRepository private constructor() :
    CrudRepository<BusinessFull, BusinessFullDao>(YelprDatabase.getInstance().businessFullDao()) {

    companion object {

        private lateinit var instance: BusinessFullRepository

        fun getInstance(): BusinessFullRepository {

            if (!::instance.isInitialized) {
                instance = BusinessFullRepository()
            }

            return instance
        }
    }

    fun get(id: String) =
        dao.get(id)
            .asLiveData()

    suspend fun getSuspended(id: String) =
        dao.getSuspended(id)
}