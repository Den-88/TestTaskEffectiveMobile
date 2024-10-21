package com.den.shak.effectivemobile

import android.app.Application
import com.den.shak.effectivemobile.di.appModule
import com.den.shak.effectivemobile.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализация Koin
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, dataModule))
        }
    }
}
