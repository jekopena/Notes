package com.jekoding.notes.framework.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo
    var title: String
)