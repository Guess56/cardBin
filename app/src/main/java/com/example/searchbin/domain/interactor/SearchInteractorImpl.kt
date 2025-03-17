package com.example.searchbin.domain.interactor

import com.example.searchbin.data.SearchInteractor
import com.example.searchbin.domain.model.CardInfo
import com.example.searchbin.domain.repository.SearchRepository
import com.example.searchbin.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchInteractorImpl(private val repository: SearchRepository): SearchInteractor {
    override suspend fun search(expression: String): Flow<Pair<List<CardInfo>?, String?>> {

        return repository.getCardInfo(expression).map { result ->
                when (result) {
                    is Resource.Success -> {
                        Pair(result.data, null)
                    }

                    is Resource.Error -> {
                        Pair(null, result.message)
                    }
            }
        }
    }
}
