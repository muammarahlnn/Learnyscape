package com.muammarahlnn.learnyscape.core.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.R


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AlertDialog, 24/08/2023 21.18 by Muammar Ahlan Abimanyu
 */

@Composable
fun BaseAlertDialog(
    title: String,
    dialogText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = stringResource(
        id = R.string.alert_dialog_confirm_button_text,
    ),
    dismissText: String = stringResource(
        id = R.string.alert_dialog_dismiss_button_text,
    ),
    onDismissRequest: (() -> Unit)? = null,
) {
    BaseAlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        content = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        confirmText = confirmText,
        dismissText = dismissText,
        modifier = modifier,
    )
}

@Composable
fun BaseAlertDialog(
    title: String,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = stringResource(
        id = R.string.alert_dialog_confirm_button_text,
    ),
    dismissText: String = stringResource(
        id = R.string.alert_dialog_dismiss_button_text,
    ),
    onDismissRequest: (() -> Unit)? = null,
) {
    BaseAlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        content = content,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        confirmText = confirmText,
        dismissText = dismissText,
        modifier = modifier,
    )
}

@Composable
fun BaseAlertDialog(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    confirmText: String = stringResource(
        id = R.string.alert_dialog_confirm_button_text,
    ),
    dismissText: String = stringResource(
        id = R.string.alert_dialog_dismiss_button_text,
    ),
    onDismissRequest: (() -> Unit)? = null,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest ?: onDismiss,
        title = title,
        text = content,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = confirmText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = dismissText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier.shadow(
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp),
        ),
    )
}