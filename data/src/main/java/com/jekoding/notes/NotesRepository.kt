package com.jekoding.notes

class NotesRepository {
    fun getNotes() : List<Note> {
        return listOf(Note("katmandu"), Note("Srilanka"))
    }
}