package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DeleteResourceDialog, 28/01/2024 15.42
 */
@Composable
internal fun DeleteResourceDialog(
    classResourceType: ClassResourceType,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val resourceName = stringResource(id = classResourceType.nameRes)
    BaseAlertDialog(
        title = stringResource(id = R.string.delete_dialog_title, resourceName),
        dialogText = stringResource(id = R.string.delete_dialog_text, resourceName),
        confirmText = stringResource(id = R.string.delete),
        onConfirm = onDelete,
        onDismiss = onDismiss,
        modifier = modifier
    )
}