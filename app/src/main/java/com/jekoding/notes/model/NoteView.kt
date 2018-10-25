package com.jekoding.notes.model

import com.jekoding.notes.Note
import com.jekoding.notes.framework.database.NoteEntity

data class NoteView(val id: Long?, val title: String) {
    companion object {
        fun from(note: Note) : NoteView {
            return NoteView(note.id, note.title)
        }

        fun from(note: NoteEntity) : NoteView {
            return NoteView(note.id, note.title)
        }
    }
}