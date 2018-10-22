package com.jekoding.notes.model

import com.jekoding.notes.Note

data class NoteView(val title: String) {
    companion object {
        fun from(note: Note) : NoteView {
            return NoteView(note.title)
        }
    }
}