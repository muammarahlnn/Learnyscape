package com.muammarahlnn.learnyscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.theme.LearnyscapeTheme
import com.muammarahlnn.learnyscape.core.domain.GetLoggedInUserUseCase
import com.muammarahlnn.learnyscape.ui.LearnyscapeApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getLoggedInUser: GetLoggedInUserUseCase

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var mainActivityUiState: MainActivityUiState
            by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainActivityUiState.onEach { uiState ->
                    mainActivityUiState = uiState
                }.collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (mainActivityUiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        setContent {
            LearnyscapeTheme {
                LearnyscapeApp(
                    isLoggedIn = isUserLogin(mainActivityUiState),
                    getLoggedInUser = getLoggedInUser,
                )
            }
        }
    }
}

@Composable
private fun isUserLogin(uiState: MainActivityUiState): Boolean =
    when (uiState) {
        MainActivityUiState.Loading -> false
        is MainActivityUiState.Success -> uiState.isLoggedIn
    }