package com.jekoding.notes.ui.main

import android.os.Bundle
import com.jekoding.notes.R
import com.jekoding.notes.ui.noteslist.NotesListFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var abcKey: BooleanKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NotesListFragment.newInstance())
                    .commitNow()
        }

        println("value of abcKey: ${abcKey.value}")
    }

}

class BooleanKey(
        val name: String,
        val value: Boolean
)