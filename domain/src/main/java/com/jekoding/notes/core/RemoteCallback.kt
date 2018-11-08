package com.jekoding.notes.core

class RemoteCallback<T>(
    val onSuccess: suspend (result: T) -> Unit,
    val onFailure: (error: Throwable) -> Unit
)