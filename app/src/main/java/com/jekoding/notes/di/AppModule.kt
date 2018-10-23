package com.jekoding.notes.di

import com.jekoding.notes.GetNotes
import com.jekoding.notes.NotesRepository
import com.jekoding.notes.ui.noteslist.NotesListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { NotesRepository() }

    single { GetNotes(get()) }

    viewModel { NotesListViewModel(get()) }
}