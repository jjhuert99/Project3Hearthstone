package com.example.project3hearthstone.di

import com.example.project3hearthstone.network.endpoints.HearthstoneEndPoint
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import com.example.project3hearthstone.network.repoimpl.HearthstoneRepoImpl
import com.example.project3hearthstone.network.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HearthstoneNetworkModule {
    private const val BASE_URL = "https://omgvamp-hearthstone-v1.p.rapidapi.com/"

    @Singleton
    @Provides
    fun provideHearthstoneRetrofit(): HearthstoneEndPoint{
        return RetrofitFactory.retrofitProvider(
            HearthstoneEndPoint::class.java,
            BASE_URL
        )
    }

    @Singleton
    @Provides
    fun provideHearthstoneRepo(dispatcher: Dispatchers, retroObject: HearthstoneEndPoint): HearthstoneRepo{
        return HearthstoneRepoImpl(dispatcher, retroObject)
    }
}
