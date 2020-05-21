package com.jianastrero.yelpr

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jianastrero.yelpr.converter.YelprTypeConverters
import com.jianastrero.yelpr.dao.BusinessDao
import com.jianastrero.yelpr.dao.BusinessFullDao
import com.jianastrero.yelpr.dao.SearchResultDao
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.model.BusinessFull
import com.jianastrero.yelpr.model.SearchResult

@Database(
    version = 1,
    entities = [
        Business::class,
        SearchResult::class,
        BusinessFull::class
    ]
)
@TypeConverters(YelprTypeConverters::class)
abstract class YelprDatabase : RoomDatabase() {

    companion object {

        @Volatile
        private lateinit var instance: YelprDatabase

        fun init(context: Context) {
            if (!::instance.isInitialized) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        YelprDatabase::class.java,
                        "yelpr_database"
                    ).build()
                }
            }
        }

        fun getInstance(): YelprDatabase = instance
    }

    abstract fun businessDao(): BusinessDao
    abstract fun searchResultDao(): SearchResultDao
    abstract fun businessFullDao(): BusinessFullDao
}