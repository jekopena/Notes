package com.jekoding.notes.core


interface UseCaseCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(error: Throwable)
}