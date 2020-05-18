package com.jianastrero.yelpr.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModelProvider

object YelprViewModelFactory {

    private lateinit var instance: ViewModelProvider.AndroidViewModelFactory

    fun init(application: Application) {
        if (!::instance.isInitialized) {
            instance = ViewModelProvider.AndroidViewModelFactory(application)
        }
    }

    fun getInstance() = instance
}