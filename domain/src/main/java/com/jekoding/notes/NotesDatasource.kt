package com.jekoding.notes

import com.jekoding.notes.models.Note

interface NotesDatasource {
    fun insert(notes: Note): Long

    fun delete(note: Note)

}