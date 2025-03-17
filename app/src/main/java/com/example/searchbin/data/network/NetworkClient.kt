package com.example.searchbin.data.network

import com.example.searchbin.data.dto.CardInfoResponse
import com.example.searchbin.data.dto.Response


interface NetworkClient {
    suspend fun doRequest(dto:Any): Response<CardInfoResponse>
}