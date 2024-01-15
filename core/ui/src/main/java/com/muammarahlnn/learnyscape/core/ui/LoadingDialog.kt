package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @Author Muammar Ahlan Abimanyu
 * @File LoadingDialog, 15/01/2024 14.06
 */
@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {
            // prevent user from dismissing the dialog when loading
        },
        confirmButton = {
            // there are no confirm button when loading
        },
        text = {
            // add padding top to make the loading well-centered due to empty confirmButton
            LoadingScreen(
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 16.dp)
            )
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
    )
}