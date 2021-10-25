package com.example.project3hearthstone.classscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ClassViewModelFactory(
    private val passedClass: String,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClassViewModel::class.java)) {
            return ClassViewModel(passedClass, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}