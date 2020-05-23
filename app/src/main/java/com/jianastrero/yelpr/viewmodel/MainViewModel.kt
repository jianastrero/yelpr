package com.jianastrero.yelpr.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jianastrero.yelpr.binding.NonNullObservableField
import com.jianastrero.yelpr.enumeration.ResponseCode
import com.jianastrero.yelpr.enumeration.SortBy
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.model.BusinessFull
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.repository.YelpRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    application: Application,
    private val yelpRepository: YelpRepository
) : AndroidViewModel(application) {

    val searchResultLiveData = MutableLiveData<SearchResult?>()
    val businessFull = MutableLiveData<BusinessFull>()
    val isDetailViewExpanded = MutableLiveData<Boolean>().apply {
        postValue(false)
    }
    val sortBy = MutableLiveData<SortBy>().apply {
        postValue(SortBy.NAME_ASC)
    }
    val isSearching = NonNullObservableField(true)
    val hasInternetConnection = NonNullObservableField(true)
    val searchTerm = NonNullObservableField("")
    val isListView = NonNullObservableField(true)

    var latitude = 0.0
    var longitude = 0.0
    var isSearchingLocation = false

    fun search() = CoroutineScope(Dispatchers.IO).launch {
        val (code, result) =
            if (isSearchingLocation) {
                yelpRepository.search(searchTerm.get())
            } else {
                yelpRepository.search(
                    latitude,
                    longitude,
                    searchTerm.get()
                )
            }

        "code: $code".log()
        "result: ${result?.total}".log()

        withContext(Dispatchers.Main) {
            searchResultLiveData.postValue(result)
            isSearching.set(false)

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

    suspend fun getBusinessFull(id: String) = withContext(Dispatchers.IO) {
        val (code, result) = yelpRepository.get(id)

        "code: $code".log()
        "result: $result".log()

        withContext(Dispatchers.Main) {
            isSearching.set(false)

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

        result
    }

    fun toggleView() {
        isListView.set(!isListView.get())
    }
}