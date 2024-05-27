package com.muammarahlnn.learnyscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.theme.LearnyscapeTheme
import com.muammarahlnn.learnyscape.core.domain.home.GetLoggedInUserUseCase
import com.muammarahlnn.learnyscape.ui.LearnyscapeApp
import com.muammarahlnn.learnyscape.ui.rememberLearnyscapeAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getLoggedInUserUseCase: GetLoggedInUserUseCase

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.invokeIsUserLoggedInUseCase()
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }


        setContent {
            val appState = rememberLearnyscapeAppState(
                getLoggedInUserUseCase = getLoggedInUserUseCase
            )

            LearnyscapeTheme {
                LearnyscapeApp(
                    appState = appState,
                    isUserLoggedIn = getIsUserLoggedInValue(uiState)
                )
            }
        }
    }

    private fun getIsUserLoggedInValue(uiState: MainActivityUiState): Boolean = when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> uiState.isUserLoggedIn
    }
}