package com.jekoding.notes.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jekoding.notes.models.NotesUser

class NotesUserManager {
    fun getCurrentUser(): NotesUser? {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        return if (firebaseUser == null) {
            null
        } else {
            parseFirebaseUser(firebaseUser)
        }
    }

    private fun parseFirebaseUser(firebaseUser: FirebaseUser): NotesUser = NotesUser(
        firebaseUser.uid,
        firebaseUser.displayName,
        firebaseUser.email
    )
}