package com.jekoding.notes.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jekoding.notes.UserManager
import com.jekoding.notes.models.NotesUser

class NotesUserManager : UserManager {
    override fun getCurrentUser(): NotesUser? =
        FirebaseAuth.getInstance().currentUser?.let { parseFirebaseUser(it) }


    override fun signout() = FirebaseAuth.getInstance().signOut()

    private fun parseFirebaseUser(firebaseUser: FirebaseUser): NotesUser = NotesUser(
        firebaseUser.uid,
        firebaseUser.displayName,
        firebaseUser.email
    )
}