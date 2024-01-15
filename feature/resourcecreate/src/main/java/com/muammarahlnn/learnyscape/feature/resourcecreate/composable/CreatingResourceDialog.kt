package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.ui.LoadingDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.CreatingResourceDialogState
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreatingResourceDialog, 15/01/2024 14.18
 */
@Composable
fun CreatingResourceDialog(
    state: CreatingResourceDialogState,
    onConfirmSuccess: () -> Unit,
    onDismiss: () -> Unit,
) {
    when (state) {
        CreatingResourceDialogState.Loading -> LoadingDialog()

        is CreatingResourceDialogState.Success -> SuccessCreatingResourceDialog(
            message = state.message,
            onConfirm = onConfirmSuccess
        )

        is CreatingResourceDialogState.Error -> ErrorCreatingResourceDialog(
            message = state.message,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun SuccessCreatingResourceDialog(
    message: String,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = {
            // prevent user from dismissing the dialog when loading
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_check),
                    contentDescription = stringResource(id = R.string.success_creating_resource),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(64.dp)
                )
            }
        },
        text = {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.shadow(
            elevation = 2.dp,
            shape = RoundedCornerShape(8.dp),
        ),
    )
}

@Composable
private fun ErrorCreatingResourceDialog(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_error),
                    contentDescription = stringResource(id = R.string.success_creating_resource),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
            }
        },
        content = {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        confirmText = stringResource(id = R.string.ok),
        dismissText = null,
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        modifier = modifier,
    )
}