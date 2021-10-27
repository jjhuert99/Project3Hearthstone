package com.example.project3hearthstone.searchresultsscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.project3hearthstone.classscreen.ClassViewModel

class SearchViewModelFactory(
    private val searchString: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultsViewModel::class.java)) {
            return SearchResultsViewModel(searchString, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}