package com.example.architecturewithcoroutine.framework

import com.example.architecturewithcoroutine.view.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModuleDI = module {
    viewModel { MainActivityViewModel() }
}