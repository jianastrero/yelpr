package com.jianastrero.yelpr.dao.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface BaseDao<MODEL> {

    @Delete
    suspend fun delete(vararg items: MODEL)

    fun get(): Flow<List<MODEL>>

    fun get(id: Int): Flow<MODEL?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: MODEL): List<Long>

    @Update
    suspend fun update(vararg items: MODEL)
}