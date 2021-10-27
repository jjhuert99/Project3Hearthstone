package com.example.project3hearthstone.searchresultsscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.network.HeartstoneApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultsViewModel(searchString: String, application: Application) : ViewModel() {
    //passed Argument
    private val _passedSearch = MutableLiveData<String>()
    val passedSearch: LiveData<String>
        get() = _passedSearch

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _searcResults = MutableLiveData<List<CardsBySearch>>()
    val searcResults: LiveData<List<CardsBySearch>>
        get() = _searcResults

    private val _navigateToOverView = MutableLiveData<String>()
    val navigateToOverView: LiveData<String>
        get() = _navigateToOverView

    fun passCardName(passName: String){
        _navigateToOverView.value = passName
    }

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _passedSearch.value = searchString
        getSearchResults()
    }

    private fun getSearchResults() {
        coroutineScope.launch {
            var getCardsDeferred =
                HeartstoneApi.retrofitService.getCardsBySearch(searchCard = _passedSearch.value!!)
            try {
                var listResult = getCardsDeferred.await()
                _searcResults.value = listResult
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }

    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
}
}