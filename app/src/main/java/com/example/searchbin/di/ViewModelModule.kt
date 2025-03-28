package com.example.searchbin.di

import com.example.searchbin.ui.EnteringViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{EnteringViewModel(get())}
}