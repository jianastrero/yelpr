package com.jianastrero.yelpr.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jianastrero.yelpr.binding.NonNullObservableField
import com.jianastrero.yelpr.enumeration.ResponseCode
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.repository.YelpRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val yelpRepository: YelpRepository
) : AndroidViewModel(application) {

    var hasInternetConnection = NonNullObservableField(true)
    var latitude = 0.0
    var longitude = 0.0
    var searchResult: SearchResult? = null
    var searchTerm = NonNullObservableField("")

    fun search() = CoroutineScope(Dispatchers.IO).launch {
        val (code, result) = yelpRepository.search(
            latitude,
            longitude,
            searchTerm.get()
        )

        "code: $code".log()
        "result: ${result?.total}".log()

        searchResult = result

        when (code) {
            ResponseCode.NO_INTERNET_CONNECTION -> {
                hasInternetConnection.set(false)
            }
            else -> {
                hasInternetConnection.set(true)
            }
        }
    }
}