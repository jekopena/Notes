package com.jekoding.notes.database.room

import androidx.room.TypeConverter
import java.util.*

class DateTypeconverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}