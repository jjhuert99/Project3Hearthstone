package com.example.project3hearthstone.cardoverview

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.favoritesdatabase.Favorite
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabaseDao
import com.example.project3hearthstone.network.HeartstoneApi
import com.example.project3hearthstone.network.SingleCard
import kotlinx.coroutines.*

class CardOverviewViewModel(passedName: String, application: Application, val database: FavoritesDatabaseDao) : ViewModel() {
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


    private var fav = MutableLiveData<Favorite?>()
    private val favs = database.getAllFav()

    init {
        _passedCardName.value = passedName
        getCardOverview()
        initializeFav()
    }

    private fun initializeFav() {
        coroutineScope.launch {
            fav.value = getFavFromDatabase()
        }
    }

    private suspend fun getFavFromDatabase(): Favorite? {
        return withContext(Dispatchers.IO){
            var fav = database.getOneFav()
            fav
        }
    }

    fun onStartTracking(){
        coroutineScope.launch{
            val newFav = Favorite()
            newFav.cardName = _singleCard.value?.get(0)?.name.toString()
            newFav.cardRarity = _singleCard.value?.get(0)?.rarity.toString()
            newFav.cardSet = _singleCard.value?.get(0)?.cardSet.toString()
            newFav.cardType = _singleCard.value?.get(0)?.type.toString()
            insert(newFav)
            fav.value = getFavFromDatabase()
        }
    }

    private suspend fun insert(newFav: Favorite) {
        withContext(Dispatchers.IO){
            database.insert(newFav)
        }
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