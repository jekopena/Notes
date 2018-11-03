package com.jekoding.notes

import com.jekoding.notes.models.Note
import java.lang.Exception

class NotesRepository(private val notesDatasource: NotesDatasource) {
    fun saveNote(note: Note): Result<Long> {
        return try {
            val noteId = notesDatasource.insert(note)
            Result.success(noteId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}