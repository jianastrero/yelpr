package com.jianastrero.yelpr.repository.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.jianastrero.yelpr.dao.base.BaseDao

open class CrudRepository<MODEL, DAO : BaseDao<MODEL>>(protected val dao: DAO) {

    suspend fun delete(vararg items: MODEL) = dao.delete(*items)

    fun get(): LiveData<List<MODEL>> =
        dao.get()
            .asLiveData()

    fun get(id: Int): LiveData<MODEL> =
        dao.get(id)
            .asLiveData()

    suspend fun insert(vararg items: MODEL) = dao.insert(*items)

    suspend fun update(vararg items: MODEL) = dao.update(*items)
}