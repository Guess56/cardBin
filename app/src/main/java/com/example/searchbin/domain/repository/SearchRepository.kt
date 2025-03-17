package com.example.searchbin.domain.repository


import com.example.searchbin.domain.model.CardInfo
import com.example.searchbin.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getCardInfo(expression: String): Flow<Resource<List<CardInfo>>>
}