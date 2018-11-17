package com.jekoding.notes.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface NoteRoomDao : BaseDao<NoteEntity> {
    @Query("SELECT * FROM noteentity")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Query("SELECT * FROM noteentity WHERE id = :noteId")
    fun getNoteById(noteId: Long): LiveData<NoteEntity>

    @Query("SELECT * FROM noteentity WHERE title LIKE :title")
    fun findByName(title: String): LiveData<List<NoteEntity>>

    @Query("DELETE FROM noteentity")
    fun deleteAllNotes()
}