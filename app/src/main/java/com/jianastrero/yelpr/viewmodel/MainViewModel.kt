package com.jianastrero.yelpr.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.jianastrero.yelpr.binding.NonNullObservableField
import com.jianastrero.yelpr.enumeration.ResponseCode
import com.jianastrero.yelpr.enumeration.SortBy
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.model.Business
import com.jianastrero.yelpr.model.BusinessFull
import com.jianastrero.yelpr.model.SearchResult
import com.jianastrero.yelpr.repository.SearchResultRepository
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

    var hasInternetConnection = NonNullObservableField(true)
    var latitude = 0.0
    var longitude = 0.0
    var searchTerm = NonNullObservableField("")
    var isListView = NonNullObservableField(true)

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

    suspend fun getBusinessFull(id: String) = withContext(Dispatchers.IO) {
        val (code, result) = yelpRepository.get(id)

        "code: $code".log()
        "result: $result".log()

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

        result
    }

    fun toggleView() {
        isListView.set(!isListView.get())
    }
}