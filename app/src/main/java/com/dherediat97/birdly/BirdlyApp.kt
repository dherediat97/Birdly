package com.dherediat97.birdly

import android.app.Application
import com.dherediat97.birdly.di.repositoriesModule
import com.dherediat97.birdly.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BirdlyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BirdlyApp)
            modules(repositoriesModule)
            modules(viewModelsModule)
        }
    }
}