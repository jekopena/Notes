package com.jekoding.notes.di

import android.content.SharedPreferences
import com.jekoding.notes.ui.main.BooleanKey
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun provideABCKey(
            preference: SharedPreferences
    ):BooleanKey {
        return BooleanKey(
                name = "abc",
                value = preference.getBoolean("abc", false)
        )
    }
}