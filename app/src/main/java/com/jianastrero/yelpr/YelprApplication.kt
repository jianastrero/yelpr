package com.jianastrero.yelpr

import android.app.Application
import com.jianastrero.yelpr.viewmodel.factory.YelprViewModelFactory

class YelprApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        YelprDatabase.init(this)
        YelprViewModelFactory.init(this)
    }
}