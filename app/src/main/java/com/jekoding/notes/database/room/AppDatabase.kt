package com.jekoding.notes.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(DateTypeconverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteRoomDao(): NoteRoomDao
}