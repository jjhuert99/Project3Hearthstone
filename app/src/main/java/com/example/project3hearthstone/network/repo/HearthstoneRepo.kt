package com.example.project3hearthstone.network.repo

import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.network.InfoData
import com.example.project3hearthstone.network.SingleCard
import com.example.project3hearthstone.network.endpoints.HearthstoneEndPoint
import com.example.project3hearthstone.network.networkmodel.ServiceResult
import com.example.project3hearthstone.network.repoimpl.HearthstoneRepoImpl
import kotlinx.coroutines.Dispatchers

interface HearthstoneRepo {
    suspend fun getClasses() : ServiceResult<InfoData?>

    suspend fun getCardsByClass(
        aClass: String
    ) : ServiceResult<List<CardsByClass>?>

    suspend fun getCardsBySearch(
        searchCard: String
    ): ServiceResult<List<CardsBySearch>?>

    suspend fun getSingleCard(
         cardName: String

    ): ServiceResult<List<SingleCard>?>

    companion object{
        fun provideHearthstoneRepo(dispatcher: Dispatchers, retroObject: HearthstoneEndPoint): HearthstoneRepo{
            return HearthstoneRepoImpl(dispatcher, retroObject)
        }
    }
}
