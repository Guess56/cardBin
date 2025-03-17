package com.example.searchbin.utils

import com.example.searchbin.domain.model.CardInfo


sealed interface ScreenState {
    object Loading : ScreenState
    data class Empty(val message: String) : ScreenState
    data class Error(val message: String) : ScreenState
    data class Content(val data: List<CardInfo>) : ScreenState
}