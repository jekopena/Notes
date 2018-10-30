package com.jekoding.notes.models

import java.util.*

data class NoteView(
    val id: Long?,
    val title: String?,
    val text: String,
    val photo: String?,
    val tag: Tag?,
    val isPinned: Boolean,
    val reminder: Date?
) {
    companion object {
        fun from(notes: List<Note>): List<NoteView> {
            return notes.map { NoteView.from(it) }
        }

        fun from(note: Note): NoteView {
            return NoteView(
                note.id,
                note.title,
                note.text,
                note.photo,
                note.tag,
                note.isPinned,
                note.reminder
            )
        }
    }
}