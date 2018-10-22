package com.jekoding.notes

class NotesRepository {
    fun getNotes(): Result<List<Note>> {
        return Result.success(listOf(Note("katmandu"), Note("Srilanka")))
    }
}