package com.example.searchbin.data.repository

import android.util.Log
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
        try {
            // Проверяем корректность входных данных
            if (expression.isEmpty() || expression.any { !it.isDigit() }) {
                emit(Resource.Error("Некорректный формат параметра BIN"))
                return@flow
            }

            // Выполняем запрос через NetworkClient
            val response = networkClient.doRequest(CardInfoRequest(expression))

            if (response.isSuccessful) {
                // Получаем тело ответа
                val cardInfoResponse = response.body()
                Log.d("123", "$cardInfoResponse")
                if (cardInfoResponse != null) {
                    // Преобразуем ответ в модель CardInfo
                    val card = listOfNotNull(cardInfoResponse.toCardInfo())
                    emit(Resource.Success(card))
                } else {
                    emit(Resource.Error("Пустое тело ответа"))
                }
            } else {
                emit(Resource.Error("Ошибка: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            // Ловим исключения и возвращаем ошибку
            Log.e("SearchRepository", "Ошибка при выполнении запроса: ${e.message}", e)
            emit(Resource.Error("Сетевая ошибка: ${e.message}"))
        }
    }
}


// Функция преобразования CardInfoDto в CardInfo
fun CardInfoDto.toCardInfo(): CardInfo? {
    return if (this.number != null && this.scheme != null) {
        CardInfo(
            number = this.number.length.toString(),
            scheme = this.scheme,
            type = this.type ?: "",
            brand = this.brand ?: "",
            prepaid = this.prepaid ?: false,
            country = this.country?.toCountryInfo() ?: CountryInfo("", "", "", "", "", 0, 0),
            bank = this.bank?.toBankInfo() ?: BankInfo("", "", "", "")
        )
    } else {
        null
    }
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

fun CardInfoResponse.toCardInfo(): CardInfo {
    return CardInfo(
        number = this.number?.length?.toString() ?: "",
        scheme = this.scheme ?: "",
        type = this.type ?: "",
        brand = this.brand ?: "",
        prepaid = false,
        country = this.country?.toCountryInfo() ?: CountryInfo("", "", "", "", "", 0, 0),
        bank = this.bank?.toBankInfo() ?: BankInfo(
            "",
            "url не указан",
            "Номер не указан",
            "Город не указан"
        )
    )
}

fun BankInfoDto.toBankInfo(): BankInfo {
    return BankInfo(
        name = this.name ?: "",
        url = this.url ?: "url не указан",
        phone = this.phone ?: "Номер не указан",
        city = this.city ?: "Город не указан"
    )
}
