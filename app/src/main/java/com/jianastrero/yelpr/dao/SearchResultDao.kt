package com.jianastrero.yelpr.dao

import androidx.room.Dao
import androidx.room.Query
import com.jianastrero.yelpr.dao.base.BaseDao
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.model.SearchResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchResultDao : BaseDao<SearchResult> {

    @Query(
        """
        SELECT * FROM search_results
        ORDER BY id asc
    """
    )
    override fun get(): Flow<List<SearchResult>>

    @Query(
        """
        SELECT * FROM search_results
        WHERE id = :id
        ORDER BY id asc
        LIMIT 1
    """
    )
    override fun get(id: Int): Flow<SearchResult>
}