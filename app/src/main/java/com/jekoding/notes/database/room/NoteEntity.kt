package com.jekoding.notes.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jekoding.notes.models.Note

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo
    var title: String
) {
    companion object {
        fun from(note: Note): NoteEntity {
            return NoteEntity(note.id, note.title)
        }

        fun parseToNote(noteEntity: NoteEntity): Note {
            return Note(noteEntity.id, noteEntity.title)
        }

        fun parseToNotes(noteEntities: List<NoteEntity>): List<Note> {
            return noteEntities.map { parseToNote(it) }
        }
    }
}