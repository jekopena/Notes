package com.jekoding.notes.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jekoding.notes.NotesRemoteDatasource
import com.jekoding.notes.UserManager
import com.jekoding.notes.core.RemoteCallback
import com.jekoding.notes.exceptions.LoginRequiredException
import com.jekoding.notes.models.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class NoteRemoteDao(
    private val remoteDatabase: FirebaseDatabase,
    private val userManager: UserManager
) : NotesRemoteDatasource {
    private val notesRef = "notes"

    override fun saveNote(note: Note): String {
        val userUid = userManager.getCurrentUser()?.uid
        if (userUid != null) {
            val databaseReference = remoteDatabase.getReference(notesRef).child(userUid)
            val key = if (note.uid == null)
                databaseReference.push().key ?: throw IOException()
            else
                note.uid!!

            databaseReference.child(key).setValue(note.copy(uid = key))
            return key
        } else {
            throw LoginRequiredException("User has to login to save a Note.")
        }
    }

    override fun getAllNotes(remoteCallback: RemoteCallback<List<Note>>) {
        val userUid = userManager.getCurrentUser()?.uid
        if (userUid != null) {
            val databaseReference = remoteDatabase.getReference(notesRef).child(userUid)
            databaseReference
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val notes = arrayListOf<Note>()
                        dataSnapshot.children.mapNotNullTo(notes) {
                            it.getValue(Note::class.java)
                        }
                        GlobalScope.launch { remoteCallback.onSuccess(notes) }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        remoteCallback.onFailure(databaseError.toException())
                    }
                })
        } else {
            throw LoginRequiredException("User has to login to get its Notes.")
        }
    }

}