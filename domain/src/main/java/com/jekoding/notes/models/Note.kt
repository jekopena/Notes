package com.jekoding.notes.models

import java.util.*

data class Note(
    val id: Long?,
    val title: String?,
    val text: String,
    val photo: String?,
    val tag: Tag?,
    val isPinned: Boolean,
    val reminder: Date?
)