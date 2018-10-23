package com.jekoding.notes.ui.noteslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jekoding.notes.GetNotes
import com.jekoding.notes.core.UseCase
import com.jekoding.notes.model.NoteView

class NotesListViewModel(
    private val getNotes: GetNotes
) : ViewModel() {
    val notes: MutableLiveData<List<NoteView>> = MutableLiveData()
    val failure: MutableLiveData<String> = MutableLiveData()

    fun loadNotes() {
        getNotes(UseCase.None()) { result ->
            result.onFailure {
                failure.value = it.message
            }
            result.onSuccess {
                notes.value = it.map { note -> NoteView.from(note) }
            }
        }
    }
}
