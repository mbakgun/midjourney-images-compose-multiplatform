package com.mbakgun.mj

import android.app.Application
import com.mbakgun.mj.di.viewModelModule
import di.initKoin
import org.koin.android.ext.koin.androidContext
import util.appContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@App)
            appContext = this@App
            modules(viewModelModule)
        }
    }
}
