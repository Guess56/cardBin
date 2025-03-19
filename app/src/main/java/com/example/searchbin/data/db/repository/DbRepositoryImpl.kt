package com.example.searchbin.data.db.repository

import com.example.searchbin.AppDataBase
import com.example.searchbin.data.db.entity.CardEntity
import com.example.searchbin.domain.model.BankInfo
import com.example.searchbin.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.sql.SQLException
import java.util.UUID

class DbRepositoryImpl(
    private val appDataBase: AppDataBase
) : DbRepository {

    override suspend fun insertCard(bankInfo: BankInfo, searchBin: String) {
        val cardEntity = bankInfo.toCardEntity(searchBin)
        appDataBase.CardDao().insertCard(cardEntity)
    }



    override suspend fun deleteCard(cardEntity: CardEntity) {
        appDataBase.CardDao().deleteCard(cardEntity)
    }

    override suspend fun getCard(): Flow<Resource<List<BankInfo>>> = flow {
        try {
            val cardEntities = appDataBase.CardDao().getCard()
            val bankInfo = cardEntities.map { it.toBankInfo() }
            emit(Resource.Success(bankInfo))
        } catch (e: SQLException) {
            emit(Resource.Error(e.message!!))
        }
    }

    override suspend fun getCardById(cardId: String): Flow<Resource<BankInfo>> = flow {
        try {
            val cardEntity = appDataBase.CardDao().getCardById(cardId)
            val bankInfo = cardEntity.toBankInfo()
            emit(Resource.Success(bankInfo))
        } catch (e: SQLException) {
            emit(Resource.Error(e.message!!))
        }
    }

    private fun CardEntity.toBankInfo(): BankInfo {
        return BankInfo(
            id = this.id,
            bin = this.bin,
            name = this.city,
            url = this.url,
            phone = this.phone,
            city = this.city
        )
    }
    private fun BankInfo.toCardEntity(searchBin: String): CardEntity {
        return CardEntity(
            id = UUID.randomUUID().toString(),
            bin = searchBin,
            url = this.url,
            phone = this.phone,
            city = this.city
        )
    }
}