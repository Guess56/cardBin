package com.example.searchbin.di


import com.example.searchbin.data.network.NetworkClient
import com.example.searchbin.data.network.RetrofitClient
import com.example.searchbin.data.network.WebApiClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<WebApiClient> {
        Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebApiClient::class.java)
    }
    single<NetworkClient> {
        RetrofitClient(get())
    }
}