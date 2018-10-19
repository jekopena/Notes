package com.jekoding.notes.ui.noteslist

import android.arch.lifecycle.ViewModel
import com.jekoding.notes.GetNotes
import com.jekoding.notes.Note

class NotesListViewModel(
        private val getNotes: GetNotes
) : ViewModel() {
    val notes = arrayListOf<Note>()

    fun loadNotes() {
        notes.addAll(getNotes())
    }
}
