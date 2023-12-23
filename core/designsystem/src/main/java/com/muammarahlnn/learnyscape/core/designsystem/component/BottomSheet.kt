package com.muammarahlnn.learnyscape.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @Author Muammar Ahlan Abimanyu
 * @File BottomSheet, 18/12/2023 06.01
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        content = content,
    )
}