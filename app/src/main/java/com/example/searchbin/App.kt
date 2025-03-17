package com.example.searchbin

import android.app.Application
import com.example.searchbin.di.dataModule
import com.example.searchbin.di.interactorModule
import com.example.searchbin.di.repositoryModule
import com.example.searchbin.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule,repositoryModule, viewModelModule)
        }
    }
}