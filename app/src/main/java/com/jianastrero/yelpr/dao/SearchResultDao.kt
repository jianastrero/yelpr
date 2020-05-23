package com.jianastrero.yelpr.dao

import androidx.room.Dao
import androidx.room.Query
import com.jianastrero.yelpr.dao.base.BaseDao
import com.jianastrero.yelpr.model.SearchResult
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchResultDao : BaseDao<SearchResult> {

    @Query(
        """
        SELECT * FROM search_results
        ORDER BY localId asc
    """
    )
    override fun get(): Flow<List<SearchResult>>

    @Query(
        """
        SELECT * FROM search_results
        WHERE localId = :id
        ORDER BY localId asc
        LIMIT 1
    """
    )
    override fun get(id: Int): Flow<SearchResult?>

    @Query(
        """
        SELECT * FROM search_results
        WHERE latitude = :latitude AND longitude = :longitude AND term = :term
        ORDER BY localId asc
        LIMIT 1
    """
    )
    fun get(latitude: Double, longitude: Double, term: String): Flow<SearchResult?>

    @Query(
        """
        SELECT * FROM search_results
        WHERE term = '' OR term is null
        ORDER BY localId desc
        LIMIT 1
    """
    )
    suspend fun getLastSuspended(): SearchResult?

    @Query(
        """
        SELECT * FROM search_results
        WHERE latitude = :latitude AND longitude = :longitude AND term = :term
        ORDER BY localId asc
        LIMIT 1
    """
    )
    suspend fun getSuspended(latitude: Double, longitude: Double, term: String): SearchResult?

    @Query(
        """
        SELECT * FROM search_results
        WHERE term = :location
        ORDER BY localId asc
        LIMIT 1
    """
    )
    suspend fun getSuspended(location: String): SearchResult?

    @Query(
        """
        DELETE FROM search_results
        WHERE latitude = :latitude AND longitude = :longitude AND term = :term
    """
    )
    suspend fun delete(latitude: Double, longitude: Double, term: String)
}