package com.jekoding.notes.usecases

import com.jekoding.notes.NotesRepository
import com.jekoding.notes.UserManager
import com.jekoding.notes.core.UseCase

class SignOut(
    private val notesRepository: NotesRepository,
    private val userManager: UserManager
) : UseCase<Boolean, UseCase.None>() {
    override suspend fun run(params: None): Result<Boolean> {
        return try {
            notesRepository.clearNotes()
            userManager.signout()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
