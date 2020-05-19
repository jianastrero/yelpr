package com.jianastrero.yelpr.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<MODEL> {

    @Delete
    fun delete(vararg items: MODEL)

    fun get()

    fun get(id: Int)

    @Insert
    fun insert(vararg items: MODEL)

    @Update
    fun update(vararg items: MODEL)
}