package com.jianastrero.yelpr.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var latitude = 0.0
    var longitude = 0.0

    private suspend fun search() = withContext(Dispatchers.IO) {

    }
}