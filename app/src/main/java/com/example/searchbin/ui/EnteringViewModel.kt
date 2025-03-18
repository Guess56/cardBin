package com.example.searchbin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbin.data.SearchInteractor
import com.example.searchbin.domain.model.CardInfo
import com.example.searchbin.utils.Resource
import com.example.searchbin.utils.ScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EnteringViewModel(val searchInteractor: SearchInteractor) : ViewModel() {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val state = MutableLiveData<ScreenState>()
    private var textInput = ""
    private var searchJob: Job? = null
    fun getState(): LiveData<ScreenState> = state


    private fun loadData(bin: String) {
        state.value = ScreenState.Loading
        viewModelScope.launch {
            searchInteractor
                .search(bin)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            processResult(resource.data?.first, resource.data?.second)
                        }
                        is Resource.Error -> {
                            processError(resource.message.toString())
                        }
                    }
                }
        }
    }

    private fun processResult(data: List<CardInfo>?, error: String?) {
        if (error != null) {
            state.postValue(ScreenState.Error(error))
        } else {
            state.postValue(ScreenState.Content(data ?: emptyList()))
        }
    }

    private fun processError(message: String) {
        state.postValue(ScreenState.Error(message))
    }

    fun searchDebounce(expression: String) {
        if (textInput == expression) {
            return
        }
        textInput = expression
        if (expression.isNotBlank()) {
            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                loadData(expression)
            }
        }
    }
}