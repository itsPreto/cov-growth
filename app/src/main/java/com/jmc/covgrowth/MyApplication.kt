package com.jmc.covgrowth

import android.app.Application
import leakcanary.AppWatcher
import leakcanary.ObjectWatcher

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // leak canary ref watcher
        val objectWatcher: ObjectWatcher = AppWatcher.objectWatcher
    }
}