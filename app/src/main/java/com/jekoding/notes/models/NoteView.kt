package com.jekoding.notes.models

import com.jekoding.notes.database.room.NoteEntity

data class NoteView(val id: Long?, val title: String) {
    companion object {
        fun from(notes: List<Note>): List<NoteView> {
            return notes.map { NoteView.from(it) }
        }

        fun from(note: Note): NoteView {
            return NoteView(note.id, note.title)
        }

        fun from(note: NoteEntity): NoteView {
            return NoteView(note.id, note.title)
        }

        fun fromEntities(notes: List<NoteEntity>): List<NoteView> {
            return notes.map { NoteView.from(it) }
        }
    }
}