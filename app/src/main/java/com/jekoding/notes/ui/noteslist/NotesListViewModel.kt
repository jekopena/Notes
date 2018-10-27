package com.jekoding.notes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jekoding.notes.database.room.AppDatabase
import com.jekoding.notes.database.room.NoteEntity
import com.jekoding.notes.models.NoteView

class NotesListViewModel(
    appDatabase: AppDatabase
) : ViewModel() {

    private var notes: LiveData<List<NoteEntity>> = appDatabase.noteRoomDao().getAllNotes()
    val noteViews: LiveData<List<NoteView>> =
        Transformations.map(notes) { NoteView.fromEntities(it) }
}
