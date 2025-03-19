package com.example.searchbin.data.db.repository

import com.example.searchbin.data.db.entity.CardEntity
import com.example.searchbin.domain.model.BankInfo
import com.example.searchbin.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    suspend fun insertCard(bankInfo: BankInfo, searchBin: String)
    suspend fun deleteCard(cardEntity: CardEntity)
    suspend fun getCard(): Flow<Resource<List<BankInfo>>>
    suspend fun getCardById(cardId: String): Flow<Resource<BankInfo>>
}