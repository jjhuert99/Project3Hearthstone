package com.example.project3hearthstone.network.repoimpl

import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.network.InfoData
import com.example.project3hearthstone.network.SingleCard
import com.example.project3hearthstone.network.endpoints.HearthstoneEndPoint
import com.example.project3hearthstone.network.networkmodel.ServiceResult
import com.example.project3hearthstone.network.repo.HearthstoneRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HearthstoneRepoImpl @Inject constructor(
    private val dispatcher: Dispatchers,
    private val retroObject: HearthstoneEndPoint
) :HearthstoneRepo{
    override suspend fun getClasses(): ServiceResult<InfoData?> {
        return withContext(dispatcher.IO){
            val dataResponse = retroObject.getClasses()
            if(dataResponse.isSuccessful){
                ServiceResult.Succes(dataResponse.body())
            }else{
                ServiceResult.Error(Exception(dataResponse.errorBody().toString()))
            }
        }
    }

    override suspend fun getCardsByClass(aClass: String): ServiceResult<List<CardsByClass>?> {
        return withContext(dispatcher.IO){
            val dataResponse = retroObject.getCardsByClass(aClass = aClass)
            if(dataResponse.isSuccessful){
                ServiceResult.Succes(dataResponse.body())
            }else{
                ServiceResult.Error(Exception(dataResponse.errorBody().toString()))
            }
        }
    }

    override suspend fun getCardsBySearch(searchCard: String): ServiceResult<List<CardsBySearch>?> {
        return withContext(dispatcher.IO){
            val dataResponse = retroObject.getCardsBySearch(searchCard = searchCard)
            if(dataResponse.isSuccessful){
                ServiceResult.Succes(dataResponse.body())
            }else{
                ServiceResult.Error(Exception(dataResponse.errorBody().toString()))
            }
        }
    }

    override suspend fun getSingleCard(cardName: String): ServiceResult<List<SingleCard>?> {
        return withContext(dispatcher.IO){
            val dataResponse = retroObject.getSingleCard(cardName = cardName)
            if(dataResponse.isSuccessful){
                ServiceResult.Succes(dataResponse.body())
            }else{
                ServiceResult.Error(Exception(dataResponse.errorBody().toString()))
            }
        }
    }
}
