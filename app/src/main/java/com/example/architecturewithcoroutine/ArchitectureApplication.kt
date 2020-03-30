package com.example.architecturewithcoroutine

import android.app.Application
import com.example.architecturewithcoroutine.framework.RetrofitService.networkModuleDi
import com.example.architecturewithcoroutine.framework.databaseModule
import com.example.architecturewithcoroutine.framework.repositoryModule
import com.example.architecturewithcoroutine.framework.viewModelModuleDI
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ArchitectureApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ArchitectureApplication? = null

        fun applicationContext(): ArchitectureApplication {
            return instance as ArchitectureApplication
        }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ArchitectureApplication)
            modules(
                listOf(
                    networkModuleDi,
                    viewModelModuleDI,
                    databaseModule,
                    repositoryModule
                )
            )
        }
        Stetho.initializeWithDefaults(this)
    }
}