package com.example.project3hearthstone.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "https://omgvamp-hearthstone-v1.p.rapidapi.com/"
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface HeartstoneApiService {
    @GET("info")
    fun getClasses(
            @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
            @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
    ): Deferred<InfoData>

    @GET("cards/classes/mage")
    fun getCardsByClass(
            @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
            @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
    ): Deferred<List<CardsByClass>>

    @GET("cards/search/{name}")
    fun getCardsBySearch(
            @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
            @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
    ): Deferred<List<CardsBySearch>>

    @GET("cards/{cardName}")
    fun getSingleCard(
            @Header("x-rapidapi-host") host: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
            @Header("x-rapidapi-key") key: String = "8ea56d2ca4mshbf724a82ce4610ep1dc054jsnd7092e043b04",
    ): Deferred<List<SingleCard>>
}

object HeartstoneApi {
    val retrofitService: HeartstoneApiService by lazy {
        retrofit.create(HeartstoneApiService::class.java)
    }
}