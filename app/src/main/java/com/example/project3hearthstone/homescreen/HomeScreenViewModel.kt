package com.example.project3hearthstone.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.network.HeartstoneApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    private val _navigateToSearchScreen = MutableLiveData<String>()
    val navigateToSearchScreen: LiveData<String>
        get() = _navigateToSearchScreen

    var liveSearch = MutableLiveData<String>()


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

    fun clickedSearch(){
        _navigateToSearchScreen.value = liveSearch.value
    }


 /*   fun navigateToClassComplete(){
        _navigateToClassScreen.value = null
    }*/
}