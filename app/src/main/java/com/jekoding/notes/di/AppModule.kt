package com.jekoding.notes.di

import android.app.Activity
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.jekoding.notes.NotesDatasource
import com.jekoding.notes.NotesRemoteDatasource
import com.jekoding.notes.NotesRepository
import com.jekoding.notes.database.NoteDao
import com.jekoding.notes.database.NoteRemoteDao
import com.jekoding.notes.database.room.AppDatabase
import com.jekoding.notes.framework.NotesUserManager
import com.jekoding.notes.ui.editNote.EditNoteViewModel
import com.jekoding.notes.ui.main.MainViewModel
import com.jekoding.notes.ui.noteslist.NotesListViewModel
import com.jekoding.notes.usecases.LoadNotes
import com.jekoding.notes.usecases.SaveNote
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    single { FirebaseDatabase.getInstance() }

    single { NotesRepository(get("noteDao"), get("noteRemoteDao")) }

    single(name = "noteDao") { NoteDao(get()) as NotesDatasource }

    single(name = "noteRemoteDao") { NoteRemoteDao(get()) as NotesRemoteDatasource}

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "notesDatabase"
        ).build()
    }

    single { NotesUserManager() }

    single { SaveNote(get()) }

    single { LoadNotes(get())}

    viewModel { MainViewModel(get(), Activity.RESULT_OK) }

    viewModel { NotesListViewModel(get(), get()) }

    viewModel { EditNoteViewModel(get()) }
}