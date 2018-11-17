package com.jekoding.notes

import com.jekoding.notes.models.NotesUser

interface UserManager {
    fun getCurrentUser() : NotesUser?
    fun signout()
}