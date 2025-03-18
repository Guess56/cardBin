package com.example.searchbin.di


import com.example.searchbin.data.network.NetworkClient
import com.example.searchbin.data.network.RetrofitClient
import com.example.searchbin.data.network.WebApiClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    // Создаем HttpLoggingInterceptor с уровнем BODY для логирования запросов и ответов
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Создаем OkHttpClient и добавляем HttpLoggingInterceptor
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Создаем Retrofit с использованием OkHttpClient
    single<WebApiClient> {
        Retrofit.Builder()
            .baseUrl("https://lookup.binlist.net/")
            .client(get<OkHttpClient>()) // Используем настроенный OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebApiClient::class.java)
    }

    // Создаем NetworkClient, который использует WebApiClient
    single<NetworkClient> {
        RetrofitClient(get())
    }
}