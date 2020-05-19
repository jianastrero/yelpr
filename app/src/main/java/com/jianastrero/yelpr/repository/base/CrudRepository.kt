package com.jianastrero.yelpr.repository.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.jianastrero.yelpr.dao.base.BaseDao

open class CrudRepository<MODEL, DAO : BaseDao<MODEL>>(protected val dao: DAO) {

    open suspend fun delete(vararg items: MODEL): Unit = dao.delete(*items)

    open fun get(): LiveData<List<MODEL>> =
        dao.get()
            .asLiveData()

    open fun get(id: Int): LiveData<MODEL?> =
        dao.get(id)
            .asLiveData()

    open suspend fun insert(vararg items: MODEL): List<Long> = dao.insert(*items)

    open suspend fun update(vararg items: MODEL): Unit = dao.update(*items)
}