package com.jekoding.notes.ui.editNote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jekoding.notes.database.room.AppDatabase
import com.jekoding.notes.database.room.NoteEntity
import com.jekoding.notes.framework.Event
import com.jekoding.notes.models.NoteView
import com.jekoding.notes.usecases.SaveNote

class EditNoteViewModel(
    private val appDatabase: AppDatabase,
    private val saveNoteUC: SaveNote
) : ViewModel() {
    var noteView: LiveData<NoteView>? = null
    val failure = MutableLiveData<Event<Throwable>>()
    val navigateBack = MutableLiveData<Event<Boolean>>()

    fun loadNoteView(noteViewId: Long) {
        if (noteViewId == 0L) {
            noteView = MutableLiveData<NoteView>().apply { value = NoteView() }
        } else {
            noteView =
                    Transformations.map(appDatabase.noteRoomDao().getNoteById(noteViewId)) { noteEntity ->
                        if (noteEntity != null) {
                            NoteView.from(NoteEntity.parseToNote(noteEntity))
                        } else {
                            failure.value = Event(Throwable("Note not found"))
                            navigateBack.value = Event(true)
                            NoteView()
                        }
                    }
        }
    }

    fun saveNote() {
        saveNoteUC(NoteView.parseToNote(noteView!!.value!!)) { result ->
            result.onFailure { error ->
                failure.value = Event(error)
            }
            result.onSuccess {
                navigateBack.value = Event(true)
            }
        }
    }
}