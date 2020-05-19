package com.jianastrero.yelpr.dao

import androidx.room.Dao
import androidx.room.Query
import com.jianastrero.yelpr.dao.base.BaseDao
import com.jianastrero.yelpr.model.Business
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao : BaseDao<Business> {

    @Query(
        """
        SELECT * FROM businesses
        ORDER BY name asc
    """
    )
    override fun get(): Flow<List<Business>>

    @Query(
        """
        SELECT * FROM businesses
        WHERE id = :id
        ORDER BY id asc
        LIMIT 1
    """
    )
    override fun get(id: Int): Flow<Business?>
}