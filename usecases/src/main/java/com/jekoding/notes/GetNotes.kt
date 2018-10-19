package com.jekoding.notes

class GetNotes(private val notesRepository: NotesRepository) {
    operator fun invoke(): List<Note> = notesRepository.getNotes()
}