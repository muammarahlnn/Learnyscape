package com.muammarahlnn.learnyscacpe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.muammarahlnn.core.designsystem.theme.LearnyscapeTheme
import com.muammarahlnn.learnyscacpe.ui.LearnyscapeApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnyscapeTheme {
                LearnyscapeApp()
            }
        }
    }
}