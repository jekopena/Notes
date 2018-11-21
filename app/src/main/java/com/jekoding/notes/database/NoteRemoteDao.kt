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
import java.util.*
import kotlin.concurrent.schedule

class NoteRemoteDao(
    private val remoteDatabase: FirebaseDatabase,
    private val userManager: UserManager
) : NotesRemoteDatasource {
    private val notesRef = "notes"
    private val timeout = 10000L

    override fun saveNote(note: Note): String {
        userManager.getCurrentUser()?.uid?.let { userUid ->
            val databaseReference = remoteDatabase.getReference(notesRef).child(userUid)
            val key = note.uid?.let { it } ?: databaseReference.push().key ?: throw IOException()

            databaseReference.child(key).setValue(note.copy(uid = key))
            return key
        } ?: throw LoginRequiredException("User has to login to save a Note.")
    }

    override fun getAllNotes(remoteCallback: RemoteCallback<List<Note>>) {
        userManager.getCurrentUser()?.uid?.let { userUid ->
            val databaseReference = remoteDatabase.getReference(notesRef).child(userUid)
            val timeoutTimer = Timer()
            val dataFetchEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val notes = arrayListOf<Note>()
                    dataSnapshot.children.mapNotNullTo(notes) {
                        it.getValue(Note::class.java)
                    }
                    GlobalScope.launch { remoteCallback.onSuccess(notes) }
                    timeoutTimer.cancel()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    remoteCallback.onFailure(databaseError.toException())
                    timeoutTimer.cancel()
                }
            }

            timeoutTimer.schedule(timeout) {
                databaseReference.removeEventListener(dataFetchEventListener)
                remoteCallback.onFailure(Throwable("Internet connection timeout"))
            }

            databaseReference.addListenerForSingleValueEvent(dataFetchEventListener)
        } ?: throw LoginRequiredException("User has to login to get its Notes.")
    }
}