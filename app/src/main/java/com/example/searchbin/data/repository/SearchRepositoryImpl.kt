package com.example.searchbin.data.repository

import com.example.searchbin.data.dto.BankInfoDto
import com.example.searchbin.data.dto.CardInfoDto
import com.example.searchbin.data.dto.CardInfoRequest
import com.example.searchbin.data.dto.CardInfoResponse
import com.example.searchbin.data.dto.CountryInfoDto
import com.example.searchbin.data.network.NetworkClient
import com.example.searchbin.domain.model.BankInfo
import com.example.searchbin.domain.model.CardInfo
import com.example.searchbin.domain.model.CountryInfo
import com.example.searchbin.domain.repository.SearchRepository
import com.example.searchbin.utils.Resource


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
) : SearchRepository {
    override suspend fun getCardInfo(expression: String): Flow<Resource<List<CardInfo>>> = flow {
        val response = networkClient.doRequest(CardInfoRequest(expression))

        when (response.resultCode) {
            200 -> {
                with(response as CardInfoResponse) {
                    val card = results.map { cardDto ->
                        cardDto.toCardInfo() // Используйте функцию toCardInfo для преобразования
                    }
                    emit(Resource.Success(card))
                }
            }
            else -> {
                emit(Resource.Error("Ошибка ${response.resultCode}"))
            }
        }
    }
}

// Функция преобразования CardInfoDto в CardInfo
fun CardInfoDto.toCardInfo(): CardInfo {
    return CardInfo(
        number = this.number.length.toString() ?: "", // Используйте number напрямую, а не length
        scheme = this.scheme ?: "",
        type = this.type ?: "",
        brand = this.brand ?: "",
        prepaid = this.prepaid ?: false,
        country = this.country?.toCountryInfo() ?: CountryInfo("", "", "", "", "", 0, 0),
        bank = this.bank?.toBankInfo() ?: BankInfo("", "", "", "")
    )
}

// Функции преобразования CountryInfoDto и BankInfoDto
fun CountryInfoDto.toCountryInfo(): CountryInfo {
    return CountryInfo(
        numeric = this.numeric ?: "",
        alpha2 = this.alpha2 ?: "",
        name = this.name ?: "",
        emoji = this.emoji ?: "",
        currency = this.currency ?: "",
        latitude = this.latitude ?: 0,
        longitude = this.longitude ?: 0
    )
}

fun BankInfoDto.toBankInfo(): BankInfo {
    return BankInfo(
        name = this.name ?: "",
        url = this.url ?: "",
        phone = this.phone ?: "",
        city = this.city ?: ""
    )
}
