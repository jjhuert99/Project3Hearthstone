package com.example.project3hearthstone.favoritesscreen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project3hearthstone.favoritesdatabase.Favorite
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabaseDao
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val app: Application,
    private val dispatcher: Dispatchers,
    private val FavDatabaseDao: FavoritesDatabaseDao
) : ViewModel() {

    private val fav = MutableLiveData<Favorite?>()
    val favs = FavDatabaseDao.getAllFav()

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
        viewModelScope.launch(dispatcher.IO) {
            fav.postValue(getFavFromDatabase())
        }
    }

    private suspend fun getFavFromDatabase(): Favorite? {
        return withContext(dispatcher.IO){
            var fav = FavDatabaseDao.getOneFav()
            fav
        }
    }

}
