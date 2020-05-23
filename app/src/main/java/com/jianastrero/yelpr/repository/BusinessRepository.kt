package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.YelprDatabase
import com.jianastrero.yelpr.dao.BusinessDao
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.repository.base.CrudRepository

class BusinessRepository private constructor() :
    CrudRepository<Business, BusinessDao>(YelprDatabase.getInstance().businessDao()) {

    companion object {

        private lateinit var instance: BusinessRepository

        fun getInstance(): BusinessRepository {

            if (!::instance.isInitialized) {
                instance = BusinessRepository()
            }

            return instance
        }
    }

    fun getBusinesses(searchResultId: Int) =
        dao.getBusinesses(searchResultId)
}