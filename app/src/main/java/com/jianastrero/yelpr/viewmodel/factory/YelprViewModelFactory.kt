package com.jianastrero.yelpr.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jianastrero.yelpr.repository.SearchResultRepository
import com.jianastrero.yelpr.repository.YelpRepository
import com.jianastrero.yelpr.viewmodel.MainViewModel
import java.lang.RuntimeException

object YelprViewModelFactory {

    private lateinit var instance: ViewModelProvider.Factory

    fun init(application: Application) {
        if (!::instance.isInitialized) {
            instance = object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return when (modelClass) {
                        MainViewModel::class.java -> {
                            MainViewModel(
                                application,
                                SearchResultRepository.getInstance(),
                                YelpRepository.getInstance()
                            )
                        }
                        else -> throw RuntimeException("Unknown ViewModel: $modelClass")
                    } as T
                }
            }
        }
    }

    fun getInstance() = instance
}