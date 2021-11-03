package com.example.project3hearthstone

import com.example.project3hearthstone.network.MapPlacesApiService
import com.example.project3hearthstone.network.RetrofitClient

object Common {
    private val GOOGLE_API_URL = "https://maps.googleapis.com/"

    val googleApiService:MapPlacesApiService
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(MapPlacesApiService::class.java)
}