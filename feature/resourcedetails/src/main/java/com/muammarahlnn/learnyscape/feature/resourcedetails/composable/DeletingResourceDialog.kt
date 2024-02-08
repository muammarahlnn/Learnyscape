package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ErrorDialog
import com.muammarahlnn.learnyscape.core.ui.LoadingDialog
import com.muammarahlnn.learnyscape.core.ui.SuccessDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.R
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DeletingResourceDialog, 29/01/2024 15.19
 */
@Composable
internal fun DeletingResourceDialog(
    state: ResourceDetailsContract.UiState,
    resourceType: ClassResourceType,
    onConfirmSuccess: () -> Unit,
    onDismiss: () -> Unit,
) {
    when (state) {
        ResourceDetailsContract.UiState.Loading -> LoadingDialog()
        
        ResourceDetailsContract.UiState.Success -> SuccessDialog(
            message = stringResource(
                id = R.string.success_deleting_dialog_text,
                stringResource(id = resourceType.nameRes)
            ),
            onConfirm = onConfirmSuccess,
        )

        is ResourceDetailsContract.UiState.Error -> ErrorDialog(
            message = state.message,
            onDismiss = onDismiss
        )
    }
}