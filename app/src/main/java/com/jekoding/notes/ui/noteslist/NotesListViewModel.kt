package com.jekoding.notes.ui.noteslist

import android.arch.lifecycle.MutableLiveData
import com.jekoding.notes.GetNotes
import com.jekoding.notes.Note
import com.jekoding.notes.core.UseCase
import com.jekoding.notes.ui.core.BaseViewModel

class NotesListViewModel(
        private val getNotes: GetNotes
) : BaseViewModel() {
    val notes: MutableLiveData<List<Note>> = MutableLiveData()

    fun loadNotes() {
        getNotes(UseCase.None()) { result ->
            result.onFailure {
                // TODO handle error
            }
            result.onSuccess {
                notes.value = it
            }
        }
    }
}
