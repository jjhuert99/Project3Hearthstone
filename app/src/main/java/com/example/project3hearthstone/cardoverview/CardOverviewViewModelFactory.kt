package com.example.project3hearthstone.cardoverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project3hearthstone.classscreen.ClassViewModel

class CardOverviewViewModelFactory(
    private val passedName: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardOverviewViewModel::class.java)) {
            return CardOverviewViewModel(passedName, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}