package com.example.searchbin.di


import com.example.searchbin.data.SearchInteractor
import com.example.searchbin.domain.interactor.SearchInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}