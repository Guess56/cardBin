package com.example.searchbin.utils

import com.example.searchbin.domain.model.BankInfo

interface HistoryScreenState {
    data class Empty(val message: String) : HistoryScreenState
    data class Content(val data: List<BankInfo>) : HistoryScreenState
}