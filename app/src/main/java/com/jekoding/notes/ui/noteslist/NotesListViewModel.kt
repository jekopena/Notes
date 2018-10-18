package com.jekoding.notes.ui.noteslist

import android.arch.lifecycle.ViewModel
import com.jekoding.notes.model.Note

class NotesListViewModel : ViewModel() {
    val notes = arrayListOf<Note>(Note("katmandu"), Note("Srilanka"))

}
