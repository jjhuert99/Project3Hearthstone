package com.example.project3hearthstone.cardoverview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.network.HeartstoneApi
import com.example.project3hearthstone.network.SingleCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardOverviewViewModel : ViewModel() {
    //coroutines setup
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _singleCard = MutableLiveData<List<SingleCard>>()
    val singleCard: LiveData<List<SingleCard>>
        get() = _singleCard

    init {
        getCardOverview()
    }

    /*private fun getCardOverview() {
        HeartstoneApi.retrofitService.getSingleCard(cardName = "Mana Bind").enqueue(object: Callback<List<SingleCard>>{
            override fun onFailure(call: Call<List<SingleCard>>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }
            override fun onResponse(call: Call<List<SingleCard>>, response: Response<List<SingleCard>>) {
                _response.value = response.body().toString()
            }
        })
        //_response.value = "Card Overview Here"
    }*/
    private fun getCardOverview() {
        coroutineScope.launch {
            var getSingleDeferred =
                HeartstoneApi.retrofitService.getSingleCard(cardName = "Mana Bind")
            try {
                var listResult = getSingleDeferred.await()
                _singleCard.value = listResult
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