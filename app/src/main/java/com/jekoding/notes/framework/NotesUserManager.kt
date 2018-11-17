package com.jekoding.notes.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jekoding.notes.UserManager
import com.jekoding.notes.models.NotesUser

class NotesUserManager : UserManager {
    override fun getCurrentUser(): NotesUser? {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        return if (firebaseUser == null) {
            null
        } else {
            parseFirebaseUser(firebaseUser)
        }
    }

    override fun signout() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun parseFirebaseUser(firebaseUser: FirebaseUser): NotesUser = NotesUser(
        firebaseUser.uid,
        firebaseUser.displayName,
        firebaseUser.email
    )
}