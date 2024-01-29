package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StartQuizButton, 28/01/2024 15.34
 */
@Composable
internal fun StartQuizButton(
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseActionButton(
        onButtonClick = onButtonClick,
        onButtonGloballyPositioned = onButtonGloballyPositioned,
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.start_quiz))
    }
}