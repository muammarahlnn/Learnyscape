package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.runtime.Composable
import com.muammarahlnn.learnyscape.core.ui.AddWorkBottomSheet
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract

/**
 * @Author Muammar Ahlan Abimanyu
 * @File OverlayDialogs, 22/02/2024 00.30
 */
@Composable
internal fun OverlayDialogs(
    overlayComposableVisibility: AssignmentSubmissionContract.OverlayComposableVisibility,
    attachmentSize: Int,
    navigateToCamera: () -> Unit,
    event: (AssignmentSubmissionContract.Event) -> Unit,
) {
    if (overlayComposableVisibility.showAddWorkBottomSheet) {
        AddWorkBottomSheet(
            onCameraActionClick = {
                navigateToCamera()
                event(AssignmentSubmissionContract.Event.OnShowAddWorkBottomSheet(false))
            },
            onUploadFileActionClick = { event(AssignmentSubmissionContract.Event.OnUploadFileActionClick) },
            onDismiss = { event(AssignmentSubmissionContract.Event.OnShowAddWorkBottomSheet(false)) },
        )
    }

    if (overlayComposableVisibility.showTurnInDialog) {
        TurnInDialog(
            attachmentsSize = attachmentSize,
            onTurnIn = {
                event(AssignmentSubmissionContract.Event.OnTurnInSubmission)
            },
            onDismiss = { event(AssignmentSubmissionContract.Event.OnShowTurnInDialog(false)) }
        )
    }

    if (overlayComposableVisibility.showSaveYourWorkInfoDialog) {
        SaveYourWorkInfoDialog(
            onDismiss = { event(AssignmentSubmissionContract.Event.OnShowSaveYourWorkInfoDialog(false)) }
        )
    }

    if (overlayComposableVisibility.showUnsubmitDialog) {
        UnsubmitDialog(
            onUnsubmit = { event(AssignmentSubmissionContract.Event.OnUnsubmitSubmission) },
            onDismiss = { event(AssignmentSubmissionContract.Event.OnShowUnsubmitDialog(false)) }
        )
    }
}