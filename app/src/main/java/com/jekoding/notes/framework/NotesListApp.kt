package com.jekoding.notes.framework

import android.app.Application
import com.jekoding.notes.di.appModule
import org.koin.android.ext.android.startKoin

class NotesListApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }
}