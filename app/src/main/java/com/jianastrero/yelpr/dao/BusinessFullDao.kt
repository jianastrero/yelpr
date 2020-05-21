package com.jianastrero.yelpr.dao

import androidx.room.Dao
import androidx.room.Query
import com.jianastrero.yelpr.dao.base.BaseDao
import com.jianastrero.yelpr.model.BusinessFull
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessFullDao : BaseDao<BusinessFull> {

    @Query(
        """
        SELECT * FROM business_fulls
        ORDER BY name asc
    """
    )
    override fun get(): Flow<List<BusinessFull>>

    @Query(
        """
        SELECT * FROM business_fulls
        WHERE id = :id
        ORDER BY id asc
        LIMIT 1
    """
    )
    override fun get(id: Int): Flow<BusinessFull?>

    @Query(
        """
        SELECT * FROM business_fulls
        WHERE id = :id
        ORDER BY id asc
        LIMIT 1
    """
    )
    fun get(id: String): Flow<BusinessFull?>

    @Query(
        """
        SELECT * FROM business_fulls
        WHERE id = :id
        ORDER BY id asc
        LIMIT 1
    """
    )
    suspend fun getSuspended(id: String): BusinessFull?
}