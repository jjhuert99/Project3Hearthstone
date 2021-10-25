package com.example.project3hearthstone.classscreen

import android.app.Application
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

class ClassViewModel(passedClassB: String, application: Application) : ViewModel() {
    //coroutines setup
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //name of class passed from click on home screen. ex: Mage
    private val _passedClass = MutableLiveData<String>()
    val passedClass: LiveData<String>
        get() = _passedClass

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _cards = MutableLiveData<CardsByClass>()
    val cards: LiveData<CardsByClass>
        get() = _cards

    init {
        _passedClass.value = passedClassB
        getCardsByClass()
    }

    private fun getCardsByClass() {
        coroutineScope.launch {
            var getCardsDeferred =
                HeartstoneApi.retrofitService.getCardsByClass(aClass = _passedClass.value!!)
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