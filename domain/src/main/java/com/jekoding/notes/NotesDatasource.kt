package com.jekoding.notes

import com.jekoding.notes.models.Note

interface NotesDatasource {
    fun insert(notes: List<Note>): List<Long>

    fun insert(note: Note): Long

    fun delete(note: Note)

    fun deleteAll()
}