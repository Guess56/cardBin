package com.example.searchbin.data.network


import com.example.searchbin.data.dto.CardInfoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface WebApiClient {
    @GET("{bin}")
    suspend fun getCardInfo(@Path("bin") bin: String): retrofit2.Response<CardInfoResponse>
}