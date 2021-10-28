package com.example.project3hearthstone.cardoverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project3hearthstone.classscreen.ClassViewModel
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabaseDao

class CardOverviewViewModelFactory(
    private val passedName: String,
    private val application: Application,
    private val database: FavoritesDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardOverviewViewModel::class.java)) {
            return CardOverviewViewModel(passedName, application, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}