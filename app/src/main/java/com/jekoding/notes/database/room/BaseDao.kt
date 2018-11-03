package com.jekoding.notes.database.room

import androidx.room.Delete
import androidx.room.Insert

interface BaseDao<T> {
    @Insert
    fun insert(objs: List<T>): List<Long>

    @Insert
    fun insert(obj: T): Long

    @Delete
    fun delete(obj: T)
}