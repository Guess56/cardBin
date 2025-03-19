package com.example.searchbin.di


import com.example.searchbin.data.db.repository.DbRepository
import com.example.searchbin.data.db.repository.DbRepositoryImpl
import com.example.searchbin.data.repository.SearchRepositoryImpl
import com.example.searchbin.domain.db.DbInteractorImpl
import com.example.searchbin.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
    single<DbRepository> {
        DbRepositoryImpl(get())
    }
}