package com.example.project3hearthstone.classscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.HeartstoneApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassViewModel : ViewModel() {

    //coroutines setup
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _cards = MutableLiveData<CardsByClass>()
    val cards: LiveData<CardsByClass>
        get() = _cards
    init{
        getCardsByClass()
    }

    /*private fun getCardsByClass(){
        HeartstoneApi.retrofitService.getCardsByClass(aClass = "Mage").enqueue(object :
            Callback<List<CardsByClass>> {
            override fun onFailure(call: Call<List<CardsByClass>>, t: Throwable) {
                _response.value = "Failure" + t.message
            }
            override fun onResponse(call: Call<List<CardsByClass>>, response: Response<List<CardsByClass>>) {
                _response.value = "Success, Cards in Class: ${response.body()?.size}"
            }
        })
        _response.value = "Cards By Class Fragment"
    }*/
    private fun getCardsByClass() {
        coroutineScope.launch {
            var getCardsDeferred = HeartstoneApi.retrofitService.getCardsByClass(aClass = "Mage")
            try {
                var listResult = getCardsDeferred.await()
                _cards.value = listResult[0]

            } catch (e: Exception) {
                _status.value = "Failure" + e.message

            }
            }
        }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}