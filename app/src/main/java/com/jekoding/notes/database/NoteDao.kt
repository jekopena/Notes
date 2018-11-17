package com.jekoding.notes.database

import com.jekoding.notes.NotesDatasource
import com.jekoding.notes.database.room.AppDatabase
import com.jekoding.notes.database.room.NoteEntity
import com.jekoding.notes.models.Note

class NoteDao(private val appDatabase: AppDatabase) : NotesDatasource {
    override fun insert(notes: List<Note>): List<Long> {
        return appDatabase.noteRoomDao().insert(notes.map { NoteEntity.from(it) })
    }

    override fun insert(note: Note): Long {
        return appDatabase.noteRoomDao().insert(NoteEntity.from(note))
    }

    override fun delete(note: Note) {
        appDatabase.noteRoomDao().delete(NoteEntity.from(note))
    }

    override fun deleteAll() {
        appDatabase.noteRoomDao().deleteAllNotes()
    }
}