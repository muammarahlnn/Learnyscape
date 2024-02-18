package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcedetails.R
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract
import kotlinx.coroutines.launch
import java.io.File
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentSubmissionBottomSheet, 04/02/2024 14.00
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StudentAssignmentContent(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
    event: (ResourceDetailsContract.Event) -> Unit,
    topAppBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val density = LocalDensity.current
    var bottomPadding by remember { mutableStateOf(0.dp) }

    var showTurnInDialog by remember { mutableStateOf(false) }
    if (showTurnInDialog) {
        TurnInDialog(
            attachmentsSize = state.assignmentSubmission.attachments.size,
            onTurnIn = {
                event(ResourceDetailsContract.Event.OnTurnInAssignmentSubmission)
                showTurnInDialog = false
            },
            onDismiss = { showTurnInDialog = false }
        )
    }

    Box(modifier = modifier) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShadowElevation = 8.dp,
            sheetShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
            sheetPeekHeight = when {
                state.assignmentSubmission.attachments.isEmpty() -> 160.dp
                state.assignmentSubmission.turnInStatus -> 180.dp
                else -> 240.dp
            },
            sheetContent = {
                SheetContent(
                    sheetState = scaffoldState.bottomSheetState,
                    uiState = state.studentAssignmentBottomSheetUiState,
                    attachments = state.assignmentSubmission.attachments,
                    isSaveStudentCurrentWorkLoading = state.isSaveStudentCurrentWorkLoading,
                    isStudentCurrentWorkChange = state.isStudentCurrentWorkChange,
                    isTurnedIn = state.assignmentSubmission.turnInStatus,
                    onSeeWorkClick = {
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onAttachmentClick = { attachment ->
                        event(ResourceDetailsContract.Event.OnAttachmentClick(attachment))
                    },
                    onRemoveAttachmentClick = { index ->
                        event(ResourceDetailsContract.Event.OnRemoveAssignmentSubmissionAttachment(index))
                    },
                    onAddWorkClick = { event(ResourceDetailsContract.Event.OnShowAddWorkBottomSheet(true)) },
                    onSaveClick = { event(ResourceDetailsContract.Event.OnSaveStudentCurrentWorkClick) },
                    onRefresh = { event(ResourceDetailsContract.Event.FetchStudentAssignmentSubmission) },
                    onUnsubmitClick = { event(ResourceDetailsContract.Event.OnUnsubmitAssignmentSubmission) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom =
                            if (state.assignmentSubmission.turnInStatus) 16.dp
                            else 16.dp + bottomPadding
                        )
                )
            },
            topBar = topAppBar,
        ) { paddingValues ->
            InstructionsContent(
                state = state,
                refreshState = refreshState,
                onStartQuizButtonClick = { event(ResourceDetailsContract.Event.OnStartQuizButtonClick) },
                onAttachmentClick = { event(ResourceDetailsContract.Event.OnAttachmentClick(it)) },
                onRefresh = { event(ResourceDetailsContract.Event.FetchResourceDetails) },
                modifier = Modifier.padding(paddingValues)
            )
        }

        if (!state.assignmentSubmission.turnInStatus) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        bottomPadding = with(density) {
                            layoutCoordinates.size.height.toDp()
                        }
                    }
            ) {
                val (actionTextResId, actionEvent) = when {
                    state.assignmentSubmission.attachments.isEmpty() ->
                        Pair(R.string.add_work) {
                            event(ResourceDetailsContract.Event.OnShowAddWorkBottomSheet(true))
                        }

                    else -> Pair(R.string.turn_in) { showTurnInDialog = true }
                }

                val isFetchAssignmentSubmissionLoading =
                    state.studentAssignmentBottomSheetUiState is ResourceDetailsContract.UiState.Loading
                val loading = state.isTurnInAssignmentSubmissionLoading || isFetchAssignmentSubmissionLoading

                SubmissionActionButton(
                    actionText = stringResource(id = actionTextResId),
                    loading = loading,
                    onClick = actionEvent,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SheetContent(
    sheetState: SheetState,
    uiState: ResourceDetailsContract.UiState,
    attachments: List<File>,
    isSaveStudentCurrentWorkLoading: Boolean,
    isStudentCurrentWorkChange: Boolean,
    isTurnedIn: Boolean,
    onSeeWorkClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    onRemoveAttachmentClick: (Int) -> Unit,
    onAddWorkClick: () -> Unit,
    onSaveClick: () -> Unit,
    onRefresh: () -> Unit,
    onUnsubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        YourWorkText()

        when (sheetState.currentValue) {
            SheetValue.PartiallyExpanded -> PartiallyExpandedSheetContent(
                attachmentsSize = attachments.size,
                onSeeWorkClick = onSeeWorkClick,
            )

            SheetValue.Expanded -> ExpandedSheetContent(
                uiState = uiState,
                attachments = attachments,
                isSaveStudentCurrentWorkLoading = isSaveStudentCurrentWorkLoading,
                isStudentCurrentWorkChange = isStudentCurrentWorkChange,
                isTurnedIn = isTurnedIn,
                onAttachmentClick = onAttachmentClick,
                onRemoveClick = onRemoveAttachmentClick,
                onAddWorkClick = onAddWorkClick,
                onSaveClick = onSaveClick,
                onRefresh = onRefresh,
                onUnsubmitClick = onUnsubmitClick,
                modifier = Modifier.weight(1f),
            )

            SheetValue.Hidden -> Unit
        }
    }
}

@Composable
private fun PartiallyExpandedSheetContent(
    attachmentsSize: Int,
    onSeeWorkClick: () -> Unit,
) {
    if (attachmentsSize > 0) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .fillMaxWidth()
                    .clickable { onSeeWorkClick() }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_attachment),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (attachmentsSize > 1) {
                        stringResource(id = R.string.plural_attachment, attachmentsSize)
                    } else stringResource(id = R.string.singular_attachment),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}

@Composable
private fun ExpandedSheetContent(
    uiState: ResourceDetailsContract.UiState,
    isSaveStudentCurrentWorkLoading: Boolean,
    isStudentCurrentWorkChange: Boolean,
    isTurnedIn: Boolean,
    attachments: List<File>,
    onAttachmentClick: (File) -> Unit,
    onRemoveClick: (Int) -> Unit,
    onAddWorkClick: () -> Unit,
    onSaveClick: () -> Unit,
    onRefresh: () -> Unit,
    onUnsubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showSaveYourWorkInfoDialog by remember { mutableStateOf(false) }
    if (showSaveYourWorkInfoDialog) {
        SaveYourWorkInfoDialog(
            onDismiss = { showSaveYourWorkInfoDialog = false }
        )
    }

    var showUnsubmitDialog by remember { mutableStateOf(false) }
    if (showUnsubmitDialog) {
        UnsubmitDialog(
            onUnsubmit = {
                onUnsubmitClick()
                showUnsubmitDialog = false
            },
            onDismiss = { showUnsubmitDialog = false },
        )
    }

    Column(modifier = modifier) {
        when (uiState) {
            ResourceDetailsContract.UiState.Loading -> LoadingScreen(
                modifier = Modifier.fillMaxSize()
            )

            ResourceDetailsContract.UiState.Success -> {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.attachments),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 13.sp,
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

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
                            SubmissionAttachmentItem(
                                index = i,
                                attachment = attachments[i],
                                isLoading = isSaveStudentCurrentWorkLoading,
                                onAttachmentClick = onAttachmentClick,
                                onRemoveClick = onRemoveClick,
                                modifier = halfWidthModifier
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            if (i + 1 < attachments.size) {
                                SubmissionAttachmentItem(
                                    index = i + 1,
                                    attachment = attachments[i + 1],
                                    isLoading = isSaveStudentCurrentWorkLoading,
                                    onAttachmentClick = onAttachmentClick,
                                    onRemoveClick = onRemoveClick,
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

                if (isSaveStudentCurrentWorkLoading) {
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
                            onClick = onSaveClick,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.background,
                            ),
                            enabled = !isSaveStudentCurrentWorkLoading && isStudentCurrentWorkChange,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = stringResource(
                                    id = if (isStudentCurrentWorkChange) R.string.save
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
                                .noRippleClickable { showSaveYourWorkInfoDialog = true }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (!isTurnedIn) {
                        OutlinedButton(
                            onClick = onAddWorkClick,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = designSystemR.drawable.ic_add),
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
                            onClick = { showUnsubmitDialog = true },
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

            is ResourceDetailsContract.UiState.Error -> ErrorScreen(
                text = uiState.message,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun SaveYourWorkInfoDialog(
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.save_your_work_dialog_title),
        dialogText = stringResource(id = R.string.save_your_work_dialog_text),
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.save_your_work_dialog_confirm_text),
        dismissText = null,
    )
}

@Composable
private fun TurnInDialog(
    attachmentsSize: Int,
    onTurnIn: () -> Unit,
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.turn_in_dialog_title),
        dialogText = stringResource(
            id = if (attachmentsSize > 1) R.string.turn_in_plural_dialog_text
                else R.string.turn_in_plural_dialog_text,
            attachmentsSize,
        ),
        onConfirm = onTurnIn,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.turn_in),
    )
}

@Composable
private fun UnsubmitDialog(
    onUnsubmit: () -> Unit,
    onDismiss: () -> Unit,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.unsubmit_dialog_title),
        dialogText = stringResource(id = R.string.unsubmit_dialog_text),
        onConfirm = onUnsubmit,
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.unsubmit),
    )
}

@Composable
private fun YourWorkText() {
    Text(
        text = stringResource(id = R.string.your_work),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Medium,
        ),
    )
}

@Composable
private fun SubmissionActionButton(
    actionText: String,
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        ),
        enabled = !loading,
        modifier = modifier,
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                strokeWidth = 3.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun SubmissionAttachmentItem(
    index: Int,
    attachment: File,
    isLoading: Boolean,
    onAttachmentClick: (File) -> Unit,
    onRemoveClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.alpha(if (isLoading) 0.15f else 1f)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.clickable(
                enabled = !isLoading
            ) { onAttachmentClick(attachment) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Icon(
                    painter = painterResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.drawable.ic_document),
                    contentDescription = stringResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.string.attachment_icon_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(bottomStart = 8.dp)
                        )
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .align(Alignment.TopEnd)
                        .clickable(
                            enabled = !isLoading
                        ) { onRemoveClick(index) }
                        .padding(
                            vertical = 4.dp,
                            horizontal = 12.dp
                        )
                ) {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_close),
                        contentDescription = stringResource(id = R.string.remove_attachment),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = attachment.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}