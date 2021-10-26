package com.example.project3hearthstone.cardoverview

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.network.HeartstoneApi
import com.example.project3hearthstone.network.SingleCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CardOverviewViewModel(passedName: String, application: Application) : ViewModel() {
    //coroutines setup
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _passedCardName = MutableLiveData<String>()
    val passedCardName: LiveData<String>
        get() = _passedCardName

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _singleCard = MutableLiveData<List<SingleCard>>()
    val singleCard: LiveData<List<SingleCard>>
        get() = _singleCard

    init {
        _passedCardName.value = passedName
        getCardOverview()
    }

    private fun getCardOverview() {
        coroutineScope.launch {
            var getSingleDeferred = HeartstoneApi.retrofitService.getSingleCard(cardName = _passedCardName.value!!)
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