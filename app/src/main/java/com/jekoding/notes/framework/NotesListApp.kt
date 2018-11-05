package com.jekoding.notes.framework

import android.app.Application
import com.jekoding.notes.di.appModule
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin

class NotesListApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeLeakCanary()
        startKoin(this, listOf(appModule))
    }

    private fun initializeLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}