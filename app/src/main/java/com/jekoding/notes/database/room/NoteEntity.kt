package com.jekoding.notes.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jekoding.notes.models.Note
import com.jekoding.notes.models.Tag
import java.util.*

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo
    var title: String?,
    @ColumnInfo
    val text: String,
    @ColumnInfo
    val photo: String?,
    @ColumnInfo
    val tag: Int?,
    @ColumnInfo
    val isPinned: Boolean,
    @ColumnInfo
    val reminder: Date?,
    @ColumnInfo
    val uid: String?
) {
    companion object {
        fun from(note: Note): NoteEntity {
            return NoteEntity(
                note.id,
                note.title,
                note.text,
                note.photo,
                note.tag?.value,
                note.isPinned,
                note.reminder,
                note.uid
            )
        }

        fun parseToNote(noteEntity: NoteEntity): Note {
            return Note(
                noteEntity.id,
                noteEntity.title,
                noteEntity.text,
                noteEntity.photo,
                if (noteEntity.tag == null) null else Tag(noteEntity.tag),
                noteEntity.isPinned,
                noteEntity.reminder,
                noteEntity.uid
            )
        }

        fun parseToNotes(noteEntities: List<NoteEntity>): List<Note> {
            return noteEntities.map { parseToNote(it) }
        }
    }
}