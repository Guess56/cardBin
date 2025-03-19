package com.example.searchbin.domain.db

import com.example.searchbin.data.db.entity.CardEntity
import com.example.searchbin.data.db.repository.DbRepository
import com.example.searchbin.domain.model.BankInfo
import com.example.searchbin.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DbInteractorImpl(private val dbRepository: DbRepository): DbInteractor {
    override suspend fun insertCard(bankInfo: BankInfo, searchBin: String) {
        return dbRepository.insertCard(bankInfo, searchBin)
    }

    override suspend fun deleteCard(cardEntity: CardEntity) {
        return dbRepository.deleteCard(cardEntity)
    }

    override suspend fun getCard(): Flow<Resource<List<BankInfo>>> {
        return dbRepository.getCard().map { result ->
            when (result) {
                is Resource.Error -> Resource.Error(result.message ?: "Неизвестная ошибка")
                is Resource.Success -> result.data?.let { Resource.Success(it) } ?: Resource.Error("Список пуст")
            }
        }
    }

    override suspend fun getCardById(cardId: String): Flow<Resource<BankInfo>> {
        return dbRepository.getCardById(cardId)
    }
}