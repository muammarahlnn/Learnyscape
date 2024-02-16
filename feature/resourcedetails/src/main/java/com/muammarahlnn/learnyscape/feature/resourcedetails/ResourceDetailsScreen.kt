package com.muammarahlnn.learnyscape.feature.resourcedetails

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.isLecturer
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.uriToFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.AddWorkBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeleteResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeletingResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.InstructionsContent
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.InstructionsContentEvent
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsPager
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsTopAppBar
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.StartQuizDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.StudentAssignmentContent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsScreen, 18/08/2023 00.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ResourceDetailsRoute(
    navigateBack: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToQuizSession: (String, Int, String, Int) -> Unit,
    navigateToSubmissionDetails: (Int, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResourceDetailsViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(ResourceDetailsContract.Event.FetchResourceDetails)
        event(ResourceDetailsContract.Event.OnGetCapturedPhoto)
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            event(
                ResourceDetailsContract.Event.OnFileSelected(
                    uriToFile(
                        context = context,
                        selectedFileUri = it
                    )
                )
            )
        }
    }

    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            is ResourceDetailsContract.Effect.ShowToast ->
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

            ResourceDetailsContract.Effect.NavigateBack ->
                navigateBack()

            ResourceDetailsContract.Effect.NavigateToCamera ->
                navigateToCamera()

            ResourceDetailsContract.Effect.OpenFiles ->
                launcher.launch("*/*")

            is ResourceDetailsContract.Effect.NavigateToQuizSession ->
                navigateToQuizSession(it.quizId, it.quizTypeOrdinal, it.quizName, it.quizDuration)

            is ResourceDetailsContract.Effect.OpenAttachment ->
                openFile(context, it.attachment)

            is ResourceDetailsContract.Effect.NavigateToSubmissionDetails ->
                navigateToSubmissionDetails(
                    it.submissionTypeOrdinal,
                    it.submissionId,
                    it.studentId,
                    it.studentName,
                )
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(ResourceDetailsContract.Event.FetchResourceDetails)
    }

    ResourceDetailsScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        modifier = modifier,
    )
}

@Composable
private fun ResourceDetailsScreen(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
    event: (ResourceDetailsContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.overlayComposableVisibility.showAddWorkBottomSheet) {
        AddWorkBottomSheet(
            onCameraActionClick = { event(ResourceDetailsContract.Event.OnCameraActionClick) },
            onUploadFileActionClick = { event(ResourceDetailsContract.Event.OnUploadFileActionClick) },
            onDismiss = { event(ResourceDetailsContract.Event.OnDismissAddWorkBottomSheet) },
        )
    }

    if (state.overlayComposableVisibility.showStartQuizDialog) {
        StartQuizDialog(
            quizName = state.name,
            onConfirm = { event(ResourceDetailsContract.Event.OnConfirmStartQuizDialog) },
            onDismiss = { event(ResourceDetailsContract.Event.OnDismissStartQuizDialog) },
        )
    }

    if (state.overlayComposableVisibility.showDeleteResourceDialog) {
        DeleteResourceDialog(
            classResourceType = state.resourceType,
            onDelete = { event(ResourceDetailsContract.Event.OnConfirmDeleteResourceDialog) },
            onDismiss = { event(ResourceDetailsContract.Event.OnDismissDeleteResourceDialog) },
        )
    }

    if (state.overlayComposableVisibility.showDeletingResourceDialog) {
        DeletingResourceDialog(
            state = state.deletingResourceUiState,
            resourceType = state.resourceType,
            onConfirmSuccess = { event(ResourceDetailsContract.Event.OnConfirmSuccessDeletingResourceDialog) },
            onDismiss = { event(ResourceDetailsContract.Event.OnDismissDeletingResourceDialog) },
        )
    }

    val instructionsContentEvent = InstructionsContentEvent(
        onStartQuizButtonClick = { event(ResourceDetailsContract.Event.OnStartQuizButtonClick) },
        onAttachmentClick = { event(ResourceDetailsContract.Event.OnAttachmentClick(it)) },
        onRefresh = { event(ResourceDetailsContract.Event.FetchResourceDetails) },
    )

    val resourceDetailsTopAppBar = @Composable {
        ResourceDetailsTopAppBar(
            titleRes = state.resourceType.nameRes,
            onBackClick = { event(ResourceDetailsContract.Event.OnBackClick) },
            onDeleteClick = { event(ResourceDetailsContract.Event.OnDeleteClick) }
        )
    }

    val isLecturer = isLecturer(LocalUserModel.current.role)
    if (isLecturer) {
        Scaffold(
            topBar = resourceDetailsTopAppBar,
            modifier = modifier,
        ) { paddingValues ->
            val contentModifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

            when (state.resourceType) {
                ClassResourceType.ANNOUNCEMENT,
                ClassResourceType.MODULE -> InstructionsContent(
                    state = state,
                    refreshState = refreshState,
                    onStartQuizButtonClick = instructionsContentEvent.onStartQuizButtonClick,
                    onAttachmentClick = instructionsContentEvent.onAttachmentClick,
                    onRefresh = instructionsContentEvent.onRefresh,
                    modifier = contentModifier
                )

                ClassResourceType.ASSIGNMENT,
                ClassResourceType.QUIZ -> ResourceDetailsPager(
                    state = state,
                    refreshState = refreshState,
                    onStartQuizButtonClick = instructionsContentEvent.onStartQuizButtonClick,
                    onAttachmentClick = instructionsContentEvent.onAttachmentClick,
                    onRefreshInstructions = instructionsContentEvent.onRefresh,
                    onRefreshStudentWork = { event(ResourceDetailsContract.Event.FetchStudentWorks) },
                    onSubmissionClick = { submissionId, studentId, studentName ->
                        event(ResourceDetailsContract.Event.OnSubmissionClick(submissionId, studentId, studentName))
                    },
                    modifier = contentModifier
                )
            }
        }
    } else {
        when (state.resourceType) {
            ClassResourceType.ASSIGNMENT -> StudentAssignmentContent(
                state = state,
                refreshState = refreshState,
                event = event,
                topAppBar = resourceDetailsTopAppBar,
                modifier = Modifier.fillMaxSize()
            )

            else -> Scaffold(
                topBar = resourceDetailsTopAppBar,
                modifier = modifier,
            ) { paddingValues ->
                InstructionsContent(
                    state = state,
                    refreshState = refreshState,
                    onStartQuizButtonClick = instructionsContentEvent.onStartQuizButtonClick,
                    onAttachmentClick = instructionsContentEvent.onAttachmentClick,
                    onRefresh = instructionsContentEvent.onRefresh,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}