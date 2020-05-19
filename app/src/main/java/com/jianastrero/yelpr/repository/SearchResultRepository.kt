package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.YelprDatabase
import com.jianastrero.yelpr.dao.BusinessDao
import com.jianastrero.yelpr.dao.SearchResultDao
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.repository.base.CrudRepository

class SearchResultRepository :
    CrudRepository<SearchResult, SearchResultDao>(YelprDatabase.getInstance().searchResultDao())