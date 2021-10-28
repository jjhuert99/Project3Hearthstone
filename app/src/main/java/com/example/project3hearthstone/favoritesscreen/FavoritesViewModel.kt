package com.example.project3hearthstone.favoritesscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3hearthstone.favoritesdatabase.Favorite
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabaseDao
import kotlinx.coroutines.*

class FavoritesViewModel(application: Application, val database: FavoritesDatabaseDao) : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val fav = MutableLiveData<Favorite?>()
    val favs = database.getAllFav()

    init{
        initializeFav()
    }
    private val _navigateToOverView = MutableLiveData<String>()
    val navigateToOverView: LiveData<String>
        get() = _navigateToOverView

    fun passCardName(passName: String){
        _navigateToOverView.value = passName
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}