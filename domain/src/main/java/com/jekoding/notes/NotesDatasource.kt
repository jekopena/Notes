package com.jekoding.notes

import com.jekoding.notes.models.Note

interface NotesDatasource {
    fun insert(notes: List<Note>)

    fun delete(note: Note)
}