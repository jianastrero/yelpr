package com.jianastrero.yelpr.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.jianastrero.yelpr.binding.NonNullObservableField
import com.jianastrero.yelpr.enumeration.ResponseCode
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.repository.SearchResultRepository
import com.jianastrero.yelpr.repository.YelpRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    application: Application,
    private val searchResultRepository: SearchResultRepository,
    private val yelpRepository: YelpRepository
) : AndroidViewModel(application) {

    var hasInternetConnection = NonNullObservableField(true)
    var latitude = 0.0
    var longitude = 0.0
    var searchTerm = NonNullObservableField("")
    var isListView = NonNullObservableField(true)

    val searchResultLiveData: MutableLiveData<SearchResult?> = MutableLiveData()
    val businessLiveData: MutableLiveData<Business?> = MutableLiveData()

    fun search() = CoroutineScope(Dispatchers.IO).launch {
        val (code, result) = yelpRepository.search(
            latitude,
            longitude,
            searchTerm.get()
        )

        "code: $code".log()
        "result: ${result?.total}".log()

        withContext(Dispatchers.Main) {
            searchResultLiveData.postValue(result)
        }

        withContext(Dispatchers.Main) {
            hasInternetConnection.set(
                when (code) {
                    ResponseCode.NO_INTERNET_CONNECTION -> {
                        false
                    }
                    else -> {
                        true
                    }
                }
            )
        }
    }

    fun toggleView() {
        isListView.set(!isListView.get())
    }
}