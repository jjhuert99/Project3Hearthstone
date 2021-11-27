package com.example.project3hearthstone.homescreen

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3hearthstone.network.networkmodel.ServiceResult
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val app: Application,
    private val dispatcher: Dispatchers,
    private val HearthstoneRepo: HearthstoneRepo
) : ViewModel() {

    private val _navYet = MutableLiveData<Boolean>()
    val navYet: LiveData<Boolean> = _navYet

    fun doneNav(){
        _navYet.value = false
    }
    fun justNav(){
        _navYet.value = true
    }
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

    fun getCardClasses() {
        viewModelScope.launch(dispatcher.IO) {
            when(val response = HearthstoneRepo.getClasses()){
                is ServiceResult.Succes ->{
                    val listResult = response.data?.classes
                    val listResult2 = mutableListOf<String>()
                    if (listResult != null) {
                        for(i in listResult){
                            if(i != "Death Knight" && i != "Dream" && i != "Neutral" && i != "Whizbang") {
                                listResult2.add(i)
                            }
                        }
                    }
                    _cardClass.postValue(listResult2)
                }
                is ServiceResult.Error ->{
                    //error
                }
                else ->{
                    //big error
                }
            }
    }
}

    fun navigateToClass(classPicked: String){
        _navigateToClassScreen.value = classPicked
    }

    fun clickedSearch(){
        _navYet.value = true
        _navigateToSearchScreen.value = liveSearch.value
    }

}

