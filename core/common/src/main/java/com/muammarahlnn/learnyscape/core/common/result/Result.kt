package com.muammarahlnn.learnyscape.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Result, 02/10/2023 20.45 by Muammar Ahlan Abimanyu
 */

sealed interface Result<out T> {

    data object Loading : Result<Nothing>

    data class Success<T>(val data: T) : Result<T>

    data class NoInternet(val message: String) : Result<Nothing>

    data class Error(
        val code: String,
        val message: String,
    ) : Result<Nothing>

    data class Exception(
        val exception: Throwable? = null,
        val message: String = "Something went wrong, please try again."
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

inline fun <reified T> Result<T>.onNoInternet(
    callback: (String) -> Unit,
) = apply {
    if (this is Result.NoInternet) {
        callback(message)
    }
}

inline fun <reified T> Result<T>.onError(
    callback: (
        code: String,
        message: String
    ) -> Unit,
) = apply {
    if (this is Result.Error) {
        callback(
            code,
            message.lowercase().replaceFirstChar { it.uppercase() }
        )
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
        when (it) {
            is NoConnectivityException -> {
                emit(
                    Result.NoInternet(
                        message = it.message,
                    )
                )
            }

            is HttpException -> {
                val connectionTimedOutCode = 522
                when (it.code()) {
                    connectionTimedOutCode -> emit(
                        Result.Error(
                            code = it.code().toString(),
                            message = "Server is currently down, please try again later.",
                        )
                    )

                    else -> {
                        val responseBody = it.response()?.errorBody()?.string()
                        val errorResponse = responseBody?.convertToErrorResponse()
                        errorResponse?.error?.let { error ->
                            emit(
                                Result.Error(
                                    code = error.code.orEmpty(),
                                    message = error.message,
                                )
                            )
                        } ?: emit(Result.Exception(it))
                    }
                }
            }

            is IOException -> {
                val networkReadTimeoutErrorCode = 598
                emit(
                    Result.Error(
                        code = networkReadTimeoutErrorCode.toString(),
                        message = "Connection timed out, please try again later.",
                    )
                )
            }

            else -> emit(Result.Exception(it))
        }
    }
}