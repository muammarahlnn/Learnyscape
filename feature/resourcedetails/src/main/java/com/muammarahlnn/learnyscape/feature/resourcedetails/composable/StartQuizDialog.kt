package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StartQuizDialog, 28/01/2024 15.31
 */
@Composable
internal fun StartQuizDialog(
    quizName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.start_quiz),
        dialogText = stringResource(
            R.string.start_quiz_dialog_text,
            quizName
        ),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        confirmText = stringResource(
            id = R.string.start_quiz_dialog_confirm_button_text,
        ),
        modifier = modifier,
    )
}