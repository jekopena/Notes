package com.jekoding.notes.models

data class NoteView(val id: Long?, val title: String) {
    companion object {
        fun from(notes: List<Note>): List<NoteView> {
            return notes.map { NoteView.from(it) }
        }

        fun from(note: Note): NoteView {
            return NoteView(note.id, note.title)
        }
    }
}