package com.example.project3hearthstone.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.HeartstoneApi
import com.example.project3hearthstone.network.InfoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModel : ViewModel() {

    //coroutines setup
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    //binded to fragment UI, TextView
    //data encapsulation
    private val _cardClass = MutableLiveData<List<String>>()
    val cardClass: LiveData<List<String>>
        get() = _cardClass

    private val _navigateToClassScreen = MutableLiveData<String>()
    val navigateToClassScreen: LiveData<String>
        get() = _navigateToClassScreen

    init{
        getCardClasses()
    }

    private fun getCardClasses() {
        coroutineScope.launch {
            var getCardsDeferred = HeartstoneApi.retrofitService.getClasses()
            try {
                var listResult = getCardsDeferred.await()
                _cardClass.value = listResult.classes
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
    }
}
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    fun navigateToClass(classPicked: String){
        _navigateToClassScreen.value = classPicked
    }

 /*   fun navigateToClassComplete(){
        _navigateToClassScreen.value = null
    }*/
}