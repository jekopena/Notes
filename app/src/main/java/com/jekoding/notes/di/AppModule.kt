package com.jekoding.notes.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jekoding.notes.NotesDatasource
import com.jekoding.notes.NotesRepository
import com.jekoding.notes.database.NoteDao
import com.jekoding.notes.database.room.AppDatabase
import com.jekoding.notes.database.room.NoteEntity
import com.jekoding.notes.ui.editNote.EditNoteViewModel
import com.jekoding.notes.ui.noteslist.NotesListViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val appModule = module {
    lateinit var appDatabase: AppDatabase

    single { NotesRepository(get("noteDao")) }

    single(name = "noteDao") { NoteDao(get()) as NotesDatasource }

    single {
        appDatabase = Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "notesDatabase"
        ).build()

        appDatabase
    }

    viewModel { NotesListViewModel(get()) }

    viewModel { EditNoteViewModel(get()) }
}