package com.jianastrero.yelpr.dao

import androidx.room.Dao
import androidx.room.Query
import com.jianastrero.yelpr.dao.base.BaseDao
import com.jianastrero.yelpr.model.Business

@Dao
interface BusinessDao : BaseDao<Business> {

    @Query(
        """
        SELECT * FROM businesses
    """
    )
    override fun get()

    @Query(
        """
        SELECT * FROM businesses
        WHERE id = :id
    """
    )
    override fun get(id: Int)
}