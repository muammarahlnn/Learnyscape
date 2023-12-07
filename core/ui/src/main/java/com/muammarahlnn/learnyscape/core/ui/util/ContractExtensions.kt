package com.muammarahlnn.learnyscape.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.common.contract.BaseContract
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ContractExtensions, 28/11/2023 22.27 by Muammar Ahlan Abimanyu
 */

data class StateDispatch<STATE, EVENT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
)

@Composable
inline fun <STATE, reified EVENT> use(
    contract: BaseContract<STATE, EVENT>
): StateDispatch<STATE, EVENT> {
    val state by contract.state.collectAsStateWithLifecycle()
    val dispatch: (EVENT) -> Unit = { event ->
        contract.event(event)
    }
    return StateDispatch(
        state = state,
        dispatch = dispatch
    )
}

@Suppress("ComposableNaming")
@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(
    callback: suspend (value: T) -> Unit
) {
   LaunchedEffect(this) {
       collectLatest(callback)
   }
}