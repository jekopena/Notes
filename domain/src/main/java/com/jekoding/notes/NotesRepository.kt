package com.jekoding.notes

import com.jekoding.notes.core.RemoteCallback
import com.jekoding.notes.core.UseCaseCallback
import com.jekoding.notes.models.Note

class NotesRepository(
    private val notesDatasource: NotesDatasource,
    private val notesRemoteDatasource: NotesRemoteDatasource
) {
    fun saveNote(note: Note): Result<Long> {
        return try {
            val noteId = notesDatasource.insert(note)
            Result.success(noteId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun loadNotes(loadNotesUCCallback: UseCaseCallback<List<Note>>) {
        notesRemoteDatasource.getAllNotes(RemoteCallback({
            try {
                notesDatasource.insert(it)
                loadNotesUCCallback.onSuccess(it)
            } catch (error: Throwable) {
                loadNotesUCCallback.onFailure(error)
            }
        }, {
            loadNotesUCCallback.onFailure(it)
        }))
    }
}