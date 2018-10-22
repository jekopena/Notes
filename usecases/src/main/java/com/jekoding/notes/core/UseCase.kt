package com.jekoding.notes.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    operator fun invoke(params: Params, onResult: (Result<Type>) -> Unit = {}) {
        GlobalScope.launch(Dispatchers.Main) {
            onResult(withContext(Dispatchers.IO) {
                run(params)
            })
        }
    }

    class None
}