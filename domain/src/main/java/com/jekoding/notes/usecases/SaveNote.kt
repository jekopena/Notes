package com.jekoding.notes.usecases

import com.jekoding.notes.NotesRepository
import com.jekoding.notes.core.UseCase
import com.jekoding.notes.models.Note

class SaveNote(private val notesRepository: NotesRepository) : UseCase<Long, Note>() {
    override suspend fun run(params: Note) = notesRepository.saveNote(params)
}
