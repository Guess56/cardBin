package com.example.searchbin.di


import com.example.searchbin.data.repository.SearchRepositoryImpl
import com.example.searchbin.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
}