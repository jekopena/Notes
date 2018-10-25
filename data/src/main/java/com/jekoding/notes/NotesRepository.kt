package com.jekoding.notes

class NotesRepository {
    fun getNotes(): Result<List<Note>> {
        return Result.success(listOf(Note(1, "katmandu"), Note(2, "Srilanka")))
    }
}