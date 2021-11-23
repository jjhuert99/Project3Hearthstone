package com.example.project3hearthstone.network.endpoints

import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.network.InfoData
import com.example.project3hearthstone.network.SingleCard
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HearthstoneEndPoint {
    @GET("info")
    suspend fun getClasses(
        @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
    ): Response<InfoData?>

    @GET("cards/classes/{aClass}")
    suspend fun getCardsByClass(
        @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
        @Path("aClass") aClass: String
    ): Response<List<CardsByClass>>

    @GET("cards/search/{searchCard}")
    suspend fun getCardsBySearch(
        @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
        @Path("searchCard") searchCard: String
    ): Response<List<CardsBySearch>>

    @GET("cards/{cardName}")
    suspend fun getSingleCard(
        @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
        @Path("cardName") cardName: String

    ): Response<List<SingleCard>>
}
