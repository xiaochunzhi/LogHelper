package com.zyc.loggerhelpter

import android.app.Application
import com.zyc.loghelper.LogHelperUtils

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        LogHelperUtils.initialize(this)
    }
}