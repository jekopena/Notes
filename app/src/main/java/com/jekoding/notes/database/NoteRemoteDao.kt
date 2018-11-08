package com.jekoding.notes.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jekoding.notes.NotesRemoteDatasource
import com.jekoding.notes.core.RemoteCallback
import com.jekoding.notes.models.Note
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteRemoteDao(val remoteDatabase: FirebaseDatabase) : NotesRemoteDatasource {
    private val notesRef = "notes"

    override fun getAllNotes(remoteCallback: RemoteCallback<List<Note>>) {
        remoteDatabase.getReference(notesRef)
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
    }

}