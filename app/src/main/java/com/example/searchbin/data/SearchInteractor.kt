package com.example.searchbin.data


import com.example.searchbin.domain.model.CardInfo
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    suspend fun search(expression: String) : Flow<Pair<List<CardInfo>?, String?>>
}