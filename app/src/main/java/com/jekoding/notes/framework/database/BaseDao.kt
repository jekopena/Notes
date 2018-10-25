package com.jekoding.notes.framework.database

import androidx.room.Delete
import androidx.room.Insert

interface BaseDao<T> {
    @Insert
    fun insert(vararg obj: T)

    @Delete
    fun delete(obj: T)
}