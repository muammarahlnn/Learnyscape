package com.muammarahlnn.learnyscape.core.network.model.response.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NetworkResult, 04/10/2023 20.37 by Muammar Ahlan Abimanyu
 */
sealed class NetworkResult<T: Any> {

    class Success<T: Any>(val data: T) : NetworkResult<T>()

    class Error<T: Any>(
        val code: String,
        val message: String
    ) : NetworkResult<T>()

    class Exception<T: Any>(val exception: Throwable) : NetworkResult<T>()
}

fun <T: Any> handleApi(
    execute: suspend () -> BaseResponse<T>
): Flow<NetworkResult<T>> = flow {
    try {
        val response = execute()
        val data = response.data
        emit(NetworkResult.Success(data))
    } catch (e: HttpException) {
        val responseBody = e.response()?.errorBody()?.string()
        val errorResponse = responseBody?.convertToErrorResponse()
        errorResponse?.error?.let { error ->
            emit(
                NetworkResult.Error(
                    code = error.code,
                    message = error.message,
                )
            )
        } ?: emit(NetworkResult.Exception(e))
    } catch (e: Throwable) {
        emit(NetworkResult.Exception(e))
    }
}

suspend fun <T: Any> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Success) {
        executable(data)
    }
}

suspend fun <T: Any> NetworkResult<T>.onError(
    executable: suspend (
        code: String,
        message: String,
    ) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error) {
        executable(code, message)
    }
}

suspend fun <T: Any> NetworkResult<T>.onException(
    executable: suspend (Throwable) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Exception) {
        executable(exception)
    }
}