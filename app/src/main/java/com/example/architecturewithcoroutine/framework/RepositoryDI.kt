package com.example.architecturewithcoroutine.framework

import com.example.architecturewithcoroutine.view.MainActivityRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        MainActivityRepository()
    }
}
