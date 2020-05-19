package com.jianastrero.yelpr.repository

import com.jianastrero.yelpr.YelprDatabase
import com.jianastrero.yelpr.dao.BusinessDao
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.repository.base.CrudRepository

class BusinessRepository :
    CrudRepository<Business, BusinessDao>(YelprDatabase.getInstance().businessDao())