package com.jekoding.notes.ui.editNote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jekoding.notes.framework.Event
import com.jekoding.notes.models.NoteView
import com.jekoding.notes.usecases.SaveNote

class EditNoteViewModel(
    val saveNoteUC: SaveNote
) : ViewModel() {
    val noteView = MutableLiveData<NoteView>().apply { value = NoteView() }
    val failure = MutableLiveData<Event<Throwable>>()
    val navigateBack = MutableLiveData<Event<Boolean>>()

    fun saveNote() {
        saveNoteUC(NoteView.parseToNote(noteView.value!!)) { result ->
            result.onFailure { error ->
                failure.value = Event(error)
            }
            result.onSuccess {
                navigateBack.value = Event(true)
            }
        }
    }
}