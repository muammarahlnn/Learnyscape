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

    data class Success<T>(val data: T) : Result<T>

    data class Error(
        val code: String,
        val message: String,
    ) : Result<Nothing>

    data class Exception(val exception: Throwable? = null) : Result<Nothing>

    object Loading : Result<Nothing>
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