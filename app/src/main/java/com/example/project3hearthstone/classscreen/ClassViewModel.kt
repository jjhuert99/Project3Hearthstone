package com.example.project3hearthstone.classscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.networkmodel.ServiceResult
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassViewModel @Inject constructor(
    private val app: Application,
    private val dispatcher: Dispatchers,
    private val HearthstoneRepo: HearthstoneRepo
) : ViewModel() {

    //name of class passed from click on home screen. ex: Mage
    private val _passedClass = MutableLiveData<String?>()
    val passedClass: LiveData<String?>
        get() = _passedClass

    //sotres responses for succes or failure for retrieveing data
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    //holds retrieved data
    private val _cards = MutableLiveData<List<CardsByClass>?>()
    val cards: LiveData<List<CardsByClass>?>
        get() = _cards

    //navigation
    private val _navigateToOverView = MutableLiveData<String>()
    val navigateToOverView: LiveData<String>
        get() = _navigateToOverView

    fun passCardName(passName: String){
        _navigateToOverView.value = passName
    }

    init {
        getCardsByClass()
    }

    private fun getCardsByClass() {
        viewModelScope.launch(dispatcher.IO) {
            when(val response = HearthstoneRepo.getCardsByClass(aClass = passedClass.value.toString())){
                is ServiceResult.Succes ->{
                    _cards.postValue(response.data)
                }
                is ServiceResult.Error ->{
                    //error
                }else ->{
                    //big error
                }
            }
        }
    }

    fun passArgs(passedClass: String) {
        _passedClass.value = passedClass
    }

}
