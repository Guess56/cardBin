package com.example.searchbin.data


import com.example.searchbin.domain.model.CardInfo
import com.example.searchbin.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    suspend fun search(expression: String) : Flow<Resource<Pair<List<CardInfo>?, String?>>>
}