package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.runtime.Composable
import com.muammarahlnn.learnyscape.core.ui.ErrorDialog
import com.muammarahlnn.learnyscape.core.ui.LoadingDialog
import com.muammarahlnn.learnyscape.core.ui.SuccessDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract.CreatingResourceDialogUiState

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreatingResourceDialog, 15/01/2024 14.18
 */
@Composable
fun CreatingResourceDialog(
    state: CreatingResourceDialogUiState,
    onConfirmSuccess: () -> Unit,
    onDismiss: () -> Unit,
) {
    when (state) {
        CreatingResourceDialogUiState.Loading -> LoadingDialog()

        is CreatingResourceDialogUiState.Success -> SuccessDialog(
            message = state.message,
            onConfirm = onConfirmSuccess,
        )

        is CreatingResourceDialogUiState.Error -> ErrorDialog(
            message = state.message,
            onDismiss = onDismiss
        )
    }
}