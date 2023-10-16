package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.common.result.Result
import com.muammarahlnn.learnyscape.core.network.model.response.base.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResultMapper, 16/10/2023 03.25 by Muammar Ahlan Abimanyu
 */

inline fun <reified T: Any, reified R> Flow<NetworkResult<T>>.toResult(
    crossinline transform: (T) -> R
): Flow<Result<R>> = this.map { networkResult ->
    when (networkResult) {
        is NetworkResult.Success -> Result.Success(
            data = transform(networkResult.data)
        )
        is NetworkResult.Error -> Result.Error(
            code = networkResult.code,
            message = networkResult.message,
        )
        is NetworkResult.Exception -> Result.Exception(
            exception = networkResult.exception
        )
    }
}.onStart {
    emit(Result.Loading)
}