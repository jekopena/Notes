package com.jekoding.notes.usecases

import com.jekoding.notes.NotesRepository
import com.jekoding.notes.core.UseCase
import com.jekoding.notes.core.UseCaseCallback
import com.jekoding.notes.models.Note

class LoadNotes(private val notesRepository: NotesRepository) :
    UseCase<UseCase.None, UseCaseCallback<List<Note>>>() {

    override suspend fun run(params: UseCaseCallback<List<Note>>): Result<None> {
        notesRepository.loadNotes(params)
        return Result.success(None())
    }
}
