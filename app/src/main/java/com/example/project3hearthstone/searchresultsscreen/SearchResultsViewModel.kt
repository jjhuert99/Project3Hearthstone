package com.example.project3hearthstone.searchresultsscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.network.networkmodel.ServiceResult
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
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
    //passed Argument
    private val _passedSearch = MutableLiveData<String>()
    val passedSearch: LiveData<String>
        get() = _passedSearch

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _searcResults = MutableLiveData<List<CardsBySearch?>?>()
    val searcResults: LiveData<List<CardsBySearch?>?>
        get() = _searcResults

    private val _navigateToOverView = MutableLiveData<String>()
    val navigateToOverView: LiveData<String>
        get() = _navigateToOverView

    fun passCardName(passName: String){
        _navigateToOverView.value = passName
    }

    var liveSearch = MutableLiveData<String>()

    private val _navigateToSearchScreen = MutableLiveData<String>()
    val navigateToSearchScreen: LiveData<String>
        get() = _navigateToSearchScreen


    fun getSearchResults() {
        viewModelScope.launch(dispatcher.IO) {
            when(val response = HearthstoneRepo.getCardsBySearch(searchCard = passedSearch.value.toString())){
                is ServiceResult.Succes ->{
                    _searcResults.postValue(response.data)
                }
                is ServiceResult.Error ->{
                    //error
                }else ->{
                //big error
            }
            }
        }

    }

    fun getSearchResults2() {
        viewModelScope.launch(dispatcher.IO) {
            when(val response =
                liveSearch.value?.let { HearthstoneRepo.getCardsBySearch(searchCard = it) }){
                is ServiceResult.Succes ->{
                    _searcResults.postValue(response.data)
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
        _passedSearch.value = passedClass
    }
}
