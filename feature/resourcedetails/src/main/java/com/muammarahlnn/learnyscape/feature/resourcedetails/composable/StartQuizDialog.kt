package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.model.ui.QuizType
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StartQuizDialog, 28/01/2024 15.31
 */

// dummy quiz type that random generated
internal val quizType by lazy {
    when ((0..1).random()) {
        0 -> QuizType.MULTIPLE_CHOICE_QUESTIONS
        else -> QuizType.PHOTO_ANSWER
    }
}

@Composable
internal fun StartQuizDialog(
    onConfirm: (Int, String, Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.start_quiz),
        dialogText = stringResource(
            R.string.start_quiz_dialog_text,
            "Lorem Ipsum Dolor Sit Amet"
        ),
        onConfirm = {
            onConfirm(
                quizType.ordinal,
                "Lorem Ipsum Dolor Sit Amat Lorem Ipsum Dolor Sit Amet",
                10
            )
        },
        onDismiss = onDismiss,
        confirmText = stringResource(
            id = R.string.start_quiz_dialog_confirm_button_text,
        ),
        modifier = modifier,
    )
}