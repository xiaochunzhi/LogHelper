package com.zyc.loggerhelpter

import android.app.Application
import com.tencent.mmkv.MMKV
import com.zyc.loghelper.LogHelperUtils

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        LogHelperUtils.initialize(this)
        MMKV.initialize(this)
        MMKV.defaultMMKV().putString("test", "test")
        LogHelperUtils.switchToAlternateLauncher(this,true)
    }
}