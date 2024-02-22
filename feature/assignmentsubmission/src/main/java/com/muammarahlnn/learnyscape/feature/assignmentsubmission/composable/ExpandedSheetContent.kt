package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ExpandedSheetContent, 19/02/2024 19.46
 */
@Composable
internal fun ExpandedSheetContent(
    state: AssignmentSubmissionContract.State,
    event: (AssignmentSubmissionContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.uiState) {
        AssignmentSubmissionContract.UiState.Loading -> LoadingScreen(
            modifier = modifier.fillMaxSize()
        )

        is AssignmentSubmissionContract.UiState.Error -> ErrorScreen(
            text = state.uiState.message,
            onRefresh = { event(AssignmentSubmissionContract.Event.FetchStudentSubmission) },
            modifier = modifier.fillMaxSize()
        )

        AssignmentSubmissionContract.UiState.Success -> Column(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.attachments),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            val attachments = state.submission.attachments
            if (attachments.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.add_work_illustration),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.no_attachments_uploaded_desc),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                for (i in attachments.indices step 2) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        val halfWidthModifier = Modifier.weight(0.5f)
                        SubmissionAttachment(
                            index = i,
                            attachment = attachments[i],
                            isLoading = state.isSaveStudentCurrentWorkLoading,
                            isTurnedIn = state.submission.turnInStatus,
                            onAttachmentClick = { event(AssignmentSubmissionContract.Event.OnAttachmentClick(it)) },
                            onRemoveClick = { event(AssignmentSubmissionContract.Event.OnRemoveAssignment(it)) },
                            modifier = halfWidthModifier
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        if (i + 1 < attachments.size) {
                            SubmissionAttachment(
                                index = i + 1,
                                attachment = attachments[i + 1],
                                isLoading = state.isSaveStudentCurrentWorkLoading,
                                isTurnedIn = state.submission.turnInStatus,
                                onAttachmentClick = { event(AssignmentSubmissionContract.Event.OnAttachmentClick(it)) },
                                onRemoveClick = { event(AssignmentSubmissionContract.Event.OnRemoveAssignment(it)) },
                                modifier = halfWidthModifier
                            )
                        } else {
                            Spacer(modifier = halfWidthModifier)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isSaveStudentCurrentWorkLoading) {
                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = MaterialTheme.colorScheme.background,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (attachments.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { event(AssignmentSubmissionContract.Event.OnSaveStudentCurrentWorkClick) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.background,
                        ),
                        enabled = !state.isSaveStudentCurrentWorkLoading && state.isStudentCurrentWorkChange,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = stringResource(
                                id = if (state.isStudentCurrentWorkChange) R.string.save
                                else R.string.saved
                            ),
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_help),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .size(24.dp)
                            .noRippleClickable {
                                event(AssignmentSubmissionContract.Event.OnShowSaveYourWorkInfoDialog(true))
                            }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (!state.submission.turnInStatus) {
                    OutlinedButton(
                        onClick = { event(AssignmentSubmissionContract.Event.OnShowAddWorkBottomSheet(true)) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_work),
                            tint = MaterialTheme.colorScheme.primary,
                        )

                        Text(
                            text = stringResource(id = R.string.add_work),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = { event(AssignmentSubmissionContract.Event.OnShowUnsubmitDialog(true)) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.unsubmit),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}