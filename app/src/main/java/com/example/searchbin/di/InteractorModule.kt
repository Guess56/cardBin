package com.example.searchbin.di


import com.example.searchbin.data.SearchInteractor
import com.example.searchbin.domain.db.DbInteractor
import com.example.searchbin.domain.db.DbInteractorImpl
import com.example.searchbin.domain.interactor.SearchInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
    single<DbInteractor> {
        DbInteractorImpl(get())
    }
}