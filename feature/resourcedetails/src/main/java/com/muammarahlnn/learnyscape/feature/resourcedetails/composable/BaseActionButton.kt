package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ActionButton, 28/01/2024 15.34
 */
@Composable
internal fun BaseActionButton(
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
    buttonContent: @Composable RowScope.() -> Unit,
) {
    BaseCard(
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onButtonGloballyPositioned(coordinates)
            }
    ) {
        Button(
            onClick = onButtonClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp,
                ),
            content = buttonContent,
        )
    }
}