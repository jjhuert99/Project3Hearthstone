package com.example.project3hearthstone.di

import com.example.project3hearthstone.network.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoroutines() = Dispatchers


    //Retrofit
    @Singleton
    @Provides
    fun provideRetrofitFactory() = RetrofitFactory
}
