package com.jekoding.notes

import com.jekoding.notes.core.RemoteCallback
import com.jekoding.notes.models.Note

interface NotesRemoteDatasource {
    fun getAllNotes(remoteCallback: RemoteCallback<List<Note>>)
}