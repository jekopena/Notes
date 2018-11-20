package com.jekoding.notes.models

import java.util.*

data class NoteView(
    var id: Long? = null,
    var title: String? = null,
    var text: String = "",
    var photo: String? = null,
    var tag: Tag? = null,
    var isPinned: Boolean = false,
    var reminder: Date? = null,
    var uid: String? = null
) {
    init {

    }
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
                note.reminder,
                note.uid
            )
        }

        fun parseToNote(noteView: NoteView): Note {
            return Note(
                noteView.id,
                noteView.title,
                noteView.text,
                noteView.photo,
                noteView.tag,
                noteView.isPinned,
                noteView.reminder,
                noteView.uid
            )
        }

        fun parseToNotes(noteViews: List<NoteView>): List<Note> {
            return noteViews.map { parseToNote(it) }
        }
    }
}