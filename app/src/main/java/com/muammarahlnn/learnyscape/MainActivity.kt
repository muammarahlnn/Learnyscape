package com.muammarahlnn.learnyscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muammarahlnn.learnyscape.core.designsystem.theme.LearnyscapeTheme
import com.muammarahlnn.learnyscape.ui.LearnyscapeApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            LearnyscapeTheme {
                LearnyscapeApp()
            }
        }
    }
}