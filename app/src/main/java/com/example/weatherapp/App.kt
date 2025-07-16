package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.di.AppModule
import com.example.weatherapp.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                AppModule().module,
                NetworkModule().module,
            )
        }
    }
}