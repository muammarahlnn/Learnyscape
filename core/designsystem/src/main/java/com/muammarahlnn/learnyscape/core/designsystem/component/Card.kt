package com.muammarahlnn.learnyscape.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file Card, 07/11/2023 21.33 by Muammar Ahlan Abimanyu
 */
@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 2.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
        ),
        content = content,
        modifier = modifier,
    )
}