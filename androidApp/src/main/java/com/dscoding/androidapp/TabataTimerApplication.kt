package com.dscoding.androidapp

import android.app.Application
import com.dscoding.tabatatimer.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class TabataTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TabataTimerApplication)
            androidLogger()
        }
    }
}