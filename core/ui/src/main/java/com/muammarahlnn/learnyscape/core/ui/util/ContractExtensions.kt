package com.muammarahlnn.learnyscape.core.ui.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.muammarahlnn.learnyscape.core.common.contract.ContractProvider
import com.muammarahlnn.learnyscape.core.common.contract.RefreshProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ContractExtensions, 28/11/2023 22.27 by Muammar Ahlan Abimanyu
 */

data class StateDispatch<STATE, EVENT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
)

@OptIn(ExperimentalMaterialApi::class)
data class RefreshState(
    val refreshing: Boolean,
    val pullRefreshState: PullRefreshState,
)

@Composable
inline fun <STATE, reified EVENT, EFFECT> use(
    contract: ContractProvider<STATE, EVENT, EFFECT>
): StateDispatch<STATE, EVENT> {
    val state by contract.state.collectAsStateWithLifecycle()
    val dispatch: (EVENT) -> Unit = { contract.event(it) }
    return StateDispatch(state, dispatch)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun use(
    refreshProvider: RefreshProvider,
    onRefresh: () -> Unit,
): RefreshState {
    val refreshing by refreshProvider.refreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh,
    )
    return RefreshState(
        refreshing = refreshing,
        pullRefreshState = pullRefreshState,
    )
}

@Composable
fun <EFFECT> CollectEffect(
    effect: Flow<EFFECT>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    coroutineContext: CoroutineContext = Dispatchers.Main.immediate,
    onEffect: suspend CoroutineScope.(effect: EFFECT) -> Unit,
) {
    LaunchedEffect(effect, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            if (coroutineContext == EmptyCoroutineContext) {
                effect.collect { onEffect(it) }
            } else {
                withContext(coroutineContext) {
                    effect.collect { onEffect(it) }
                }
            }
        }
    }
}

