package com.example.searchbin.data.network



import com.example.searchbin.data.dto.CardInfoRequest
import com.example.searchbin.data.dto.CardInfoResponse
import com.example.searchbin.data.dto.Response


class RetrofitClient(private val webApiClient: WebApiClient) : NetworkClient {
    override suspend fun doRequest(request: CardInfoRequest): retrofit2.Response<CardInfoResponse> {
        return webApiClient.getCardInfo(request.expression)
    }
}