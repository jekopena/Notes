package com.jekoding.notes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jekoding.notes.GetNotes
import com.jekoding.notes.core.UseCase
import com.jekoding.notes.framework.database.AppDatabase
import com.jekoding.notes.framework.database.NoteEntity
import com.jekoding.notes.model.NoteView

class NotesListViewModel(
    private val getNotes: GetNotes,
    appDatabase: AppDatabase
) : ViewModel() {
    
    private val noteEntities: LiveData<List<NoteEntity>> = appDatabase.noteDao().getAllNotes()
    val noteViews: LiveData<List<NoteView>> = Transformations.map(noteEntities) {
        val noteViews = ArrayList<NoteView>()
        for (noteEntity in it) {
            noteViews.add(NoteView.from(noteEntity))
        }
        noteViews
    }

//    val failure: MutableLiveData<String> = MutableLiveData()
//
//    fun loadNotes() {
//        getNotes(UseCase.None()) { result ->
//            result.onFailure {
//                failure.value = it.message
//            }
//            result.onSuccess {
//                noteEntities.value = it.map { note -> NoteView.from(note) }
//            }
//        }
//    }
}
