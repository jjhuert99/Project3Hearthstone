package com.example.project3hearthstone.searchresultsscreen

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

class SearchResultsViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _searcResults = MutableLiveData<List<CardsBySearch>>()
    val searcResults: LiveData<List<CardsBySearch>>
        get() = _searcResults

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getSearchResults()
    }

    private fun getSearchResults() {
        /*HeartstoneApi.retrofitService.getCardsBySearch(searchCard = "Druid").enqueue(object: Callback<List<CardsBySearch>>{
            override fun onFailure(call: Call<List<CardsBySearch>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
            override fun onResponse(call: Call<List<CardsBySearch>>, response: Response<List<CardsBySearch>>) {
                _response.value = "Found this many: " + response.body()?.size
            }
        })*/
        coroutineScope.launch {
            var getCardsDeferred =
                HeartstoneApi.retrofitService.getCardsBySearch(searchCard = "Druid")
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