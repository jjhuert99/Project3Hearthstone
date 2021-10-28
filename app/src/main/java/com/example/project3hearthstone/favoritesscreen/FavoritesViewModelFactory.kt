package com.example.project3hearthstone.favoritesscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabaseDao

class FavoritesViewModelFactory(
    private val application: Application,
    private val database: FavoritesDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(application, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}