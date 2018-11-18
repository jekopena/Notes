package com.jekoding.notes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jekoding.notes.core.UseCaseCallback
import com.jekoding.notes.database.room.AppDatabase
import com.jekoding.notes.database.room.NoteEntity
import com.jekoding.notes.framework.Event
import com.jekoding.notes.models.Note
import com.jekoding.notes.models.NoteView
import com.jekoding.notes.usecases.LoadNotes

class NotesListViewModel(
    appDatabase: AppDatabase,
    private val loadNotesUC: LoadNotes
) : ViewModel() {
    var progressBarVisibility = MutableLiveData<Boolean>()
    val failure = MutableLiveData<Event<Throwable>>()
    private var notes: LiveData<List<NoteEntity>> = appDatabase.noteRoomDao().getAllNotes()
    val noteViews: LiveData<List<NoteView>> =
        Transformations.map(notes) { NoteView.from(NoteEntity.parseToNotes(it)) }
    val navigateToAddNote = MutableLiveData<Event<Boolean>>()

    fun addNoteClick() {
        navigateToAddNote.value = Event(true)
    }

    fun loadNotes() {
        progressBarVisibility.value = true
        loadNotesUC(object : UseCaseCallback<List<Note>> {
            override fun onSuccess(result: List<Note>) {
                progressBarVisibility.postValue(false)
            }

            override fun onFailure(error: Throwable) {
                failure.postValue(Event(error))
            }
        })
    }
}
