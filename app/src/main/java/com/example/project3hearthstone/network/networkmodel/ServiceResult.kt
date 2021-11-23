package com.example.project3hearthstone.network.networkmodel

import java.lang.Exception

sealed class ServiceResult<out R> {
    data class Succes<out T>(val data: T) : ServiceResult<T>()
    data class Error(val exception: Exception) : ServiceResult<Nothing>()
}
