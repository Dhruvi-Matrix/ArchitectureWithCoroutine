package com.example.architecturewithcoroutine.framework

import androidx.room.Room
import com.example.architecturewithcoroutine.ArchitectureApplication
import com.example.architecturewithcoroutine.data.database.PostDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {

    single {
        Room.databaseBuilder(
            (androidApplication() as ArchitectureApplication),
            PostDatabase::class.java,
            "demo.db"
        ).build()
    }

    single { (get() as PostDatabase).movieDao() }

}
