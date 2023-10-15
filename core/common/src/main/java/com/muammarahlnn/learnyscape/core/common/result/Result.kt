package com.muammarahlnn.learnyscape.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Result, 02/10/2023 20.45 by Muammar Ahlan Abimanyu
 */

sealed interface Result<out T> {

    data object Loading : Result<Nothing>

    data class Success<T>(val data: T) : Result<T>

    data class Error(
        val code: String,
        val message: String,
    ) : Result<Nothing>

    data class Exception(
        val exception: Throwable? = null,
        val message: String = "System is busy, please try again later"
    ) : Result<Nothing>
}

inline fun <reified T> Result<T>.onLoading(
    callback: () -> Unit
) = apply {
    if (this is Result.Loading) {
        callback()
    }
}

inline fun <reified T> Result<T>.onSuccess(
    callback: (data: T) -> Unit,
) = apply {
    if (this is Result.Success) {
        callback(data)
    }
}

inline fun <reified T> Result<T>.onError(
    callback: (
        code: String,
        message: String
    ) -> Unit,
) = apply {
    if (this is Result.Error) {
        callback(code, message)
    }
}

inline fun <reified T> Result<T>.onException(
    callback: (
        exception: Throwable?,
        message: String,
    ) -> Unit,
) = apply {
    if (this is Result.Exception) {
        callback(exception, message)
    }
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this.map<T, Result<T>> {
        Result.Success(it)
    }.onStart {
        emit(Result.Loading)
    }.catch {
        emit(Result.Exception(it))
    }
}