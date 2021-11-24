package com.example.project3hearthstone.ui.cardoverview.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3hearthstone.favoritesdatabase.Favorite
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabaseDao
import com.example.project3hearthstone.network.SingleCard
import com.example.project3hearthstone.network.networkmodel.ServiceResult
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CardOverviewViewModel @Inject constructor(
    private val app: Application,
    private val dispatcher: Dispatchers,
    private val HearthstoneRepo: HearthstoneRepo,
    private val FavDatabaseDao: FavoritesDatabaseDao
) : ViewModel() {

    private val _passedCardName = MutableLiveData<String>()
    val passedCardName: LiveData<String>
        get() = _passedCardName

    private val _isPresent = MutableLiveData<Boolean>()
    val isPresent: LiveData<Boolean>
        get() = _isPresent

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private var _singleCard = MutableLiveData<List<SingleCard>?>()
    val singleCard: LiveData<List<SingleCard>?>
        get() = _singleCard


    private var fav = MutableLiveData<Favorite?>()
    private val favs = FavDatabaseDao.getAllFav()

    init {
        initializeFav()
    }

    fun initializeFav() {
        viewModelScope.launch(dispatcher.IO) {
            fav.postValue(getFavFromDatabase())
        }
    }

    private suspend fun getFavFromDatabase(): Favorite? {
        return withContext(dispatcher.IO) {
            var fav = FavDatabaseDao.getOneFav()
            fav
        }
    }

    fun onStartTracking() {
        viewModelScope.launch(dispatcher.IO) {
            if (checkFor(_singleCard.value?.get(0)?.name.toString())) {
                deleteOne(_singleCard.value?.get(0)?.name.toString())
            } else {
                val newFav = Favorite(
                    cardName = _singleCard.value?.get(0)?.name.toString(),
                    cardRarity = _singleCard.value?.get(0)?.rarity.toString(),
                    cardSet = _singleCard.value?.get(0)?.cardSet.toString(),
                    cardType = _singleCard.value?.get(0)?.type.toString()
                )
                insert(newFav)
                fav.postValue(getFavFromDatabase())
            }
        }
    }

    private suspend fun deleteOne(toString: String) {
        withContext(dispatcher.IO) {
            FavDatabaseDao.clearOne(toString)
        }
    }

    private suspend fun checkFor(toString: String): Boolean {
        return withContext(dispatcher.IO) {
            val check = FavDatabaseDao.getByName(toString)
            check
        }
    }

    private suspend fun insert(newFav: Favorite) {
        withContext(dispatcher.IO) {
            FavDatabaseDao.insert(newFav)
        }
    }

    private var _cType = MutableLiveData<String?>(" ")
    val cType: LiveData<String?>
        get() = _cType

    private var _cRarity = MutableLiveData<String?>(" ")
    val cRarity: LiveData<String?>
        get() = _cRarity

    private var _cSet = MutableLiveData<String?>(" ")
    val cSet: LiveData<String?>
        get() = _cSet

    private var _cEffect = MutableLiveData<String?>(" ")
    val cEffect: LiveData<String?>
        get() = _cEffect

    fun getCardOverview() {
        viewModelScope.launch(dispatcher.IO) {
            when (val response =
                HearthstoneRepo.getSingleCard(cardName = _passedCardName.value.toString())) {
                is ServiceResult.Succes -> {
                    _singleCard.postValue(response.data)
                    if(!response.data?.get(0)?.type.isNullOrEmpty()){
                        _cType.postValue("Type: "+response.data?.get(0)?.type)
                    }
                    if(!response.data?.get(0)?.rarity.isNullOrEmpty()){
                        _cRarity.postValue("Rarity: "+response.data?.get(0)?.rarity)
                    }
                    if(!response.data?.get(0)?.cardSet.isNullOrEmpty()){
                        _cSet.postValue("Set: "+response.data?.get(0)?.cardSet)
                    }
                    if(!response.data?.get(0)?.text.isNullOrEmpty()){
                        _cEffect.postValue("Effect: "+response.data?.get(0)?.text)
                    }
                }
                is ServiceResult.Error -> {
                    //error
                }
                else -> {
                    //big error
                }
            }
        }
    }

    fun passArgs(cardNamePassed: String) {
        _passedCardName.value = cardNamePassed
    }
}
