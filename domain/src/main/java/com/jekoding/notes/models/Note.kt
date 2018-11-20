package com.jekoding.notes.models

import java.util.*

data class Note(
    val id: Long? = null,
    val title: String? = null,
    val text: String = "",
    val photo: String? = null,
    val tag: Tag? = null,
    val isPinned: Boolean = false,
    val reminder: Date? = null,
    val uid: String? = null
)