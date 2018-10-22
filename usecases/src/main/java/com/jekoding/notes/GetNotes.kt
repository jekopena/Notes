package com.jekoding.notes

import com.jekoding.notes.core.UseCase
import com.jekoding.notes.core.UseCase.None

class GetNotes(private val notesRepository: NotesRepository) : UseCase<List<Note>, None>() {
    override suspend fun run(params: None) = notesRepository.getNotes()
}