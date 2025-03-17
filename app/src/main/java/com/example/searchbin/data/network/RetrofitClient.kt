package com.example.searchbin.data.network



import com.example.searchbin.data.dto.CardInfoRequest
import com.example.searchbin.data.dto.CardInfoResponse
import com.example.searchbin.data.dto.Response


class RetrofitClient(private val webApiClient: WebApiClient) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response<CardInfoResponse> {
        return when (dto) {
            is CardInfoRequest -> {
                try {
                    val apiResponse = webApiClient.getCardInfo(dto.expression)
                    if (apiResponse.isSuccessful) {
                        Response<CardInfoResponse>().apply {
                            resultCode = apiResponse.code()
                            body = apiResponse.body()
                        }
                    } else {
                        val errorBody = apiResponse.errorBody()?.string()
                        Response<CardInfoResponse>().apply {
                            resultCode = apiResponse.code()
                            errorMessage = errorBody
                        }
                    }
                } catch (e: Exception) {
                    Response<CardInfoResponse>().apply {
                        resultCode = -1
                        errorMessage = e.message
                    }
                }
            }
            else -> throw IllegalArgumentException("Unsupported request type")
        }
    }
}