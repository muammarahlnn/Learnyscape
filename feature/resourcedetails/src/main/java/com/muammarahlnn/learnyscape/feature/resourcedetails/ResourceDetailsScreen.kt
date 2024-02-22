package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.isLecturer
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionSheet
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeleteResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeletingResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.InstructionsContent
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.InstructionsContentEvent
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsPager
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsTopAppBar
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.StartQuizDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.getStudentWorkType


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsScreen, 18/08/2023 00.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ResourceDetailsRoute(
    controller: ResourceDetailsController,
    modifier: Modifier = Modifier,
    viewModel: ResourceDetailsViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(ResourceDetailsContract.Event.FetchResourceDetails)
    }

    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            ResourceDetailsNavigation.NavigateBack ->
                controller.navigateBack()

            ResourceDetailsNavigation.NavigateToCamera ->
                controller.navigateToCamera()

            is ResourceDetailsNavigation.NavigateToQuizSession ->
                controller.navigateToQuizSession(
                    navigation.quizId,
                    navigation.quizTypeOrdinal,
                    navigation.quizName,
                    navigation.quizDuration,
                )

            is ResourceDetailsNavigation.NavigateToSubmissionDetails ->
                controller.navigateToSubmissionDetails(
                    navigation.submissionTypeOrdinal,
                    navigation.submissionId,
                    navigation.studentId,
                    navigation.studentName,
                )
        }
    }

    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is ResourceDetailsContract.Effect.OpenAttachment ->
                openFile(context, effect.attachment)
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(ResourceDetailsContract.Event.FetchResourceDetails)
    }

    ResourceDetailsScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@Composable
private fun ResourceDetailsScreen(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
    event: (ResourceDetailsContract.Event) -> Unit,
    navigate: (ResourceDetailsNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.overlayComposableVisibility.showStartQuizDialog) {
        StartQuizDialog(
            quizName = state.name,
            onConfirm = {
                event(ResourceDetailsContract.Event.OnDismissStartQuizDialog)

                navigate(ResourceDetailsNavigation.NavigateToQuizSession(
                    quizId = state.resourceId,
                    quizTypeOrdinal = state.quizType.ordinal,
                    quizName = state.name,
                    quizDuration = state.quizDuration,
                ))
            },
            onDismiss = { event(ResourceDetailsContract.Event.OnDismissStartQuizDialog) },
        )
    }

    if (state.overlayComposableVisibility.showDeleteResourceDialog) {
        DeleteResourceDialog(
            classResourceType = state.resourceType,
            onDelete = {
                navigate(ResourceDetailsNavigation.NavigateBack)
                event(ResourceDetailsContract.Event.OnConfirmDeleteResourceDialog)
            },
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
            onBackClick = { navigate(ResourceDetailsNavigation.NavigateBack) },
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
                        val resolvedSubmissionId = when (state.resourceType) {
                            ClassResourceType.ASSIGNMENT -> submissionId
                            ClassResourceType.QUIZ -> state.resourceId
                            else -> throw IllegalArgumentException("Only assignment and quiz is expected")
                        }

                        navigate(ResourceDetailsNavigation.NavigateToSubmissionDetails(
                            submissionTypeOrdinal = getStudentWorkType(state.resourceType).ordinal,
                            submissionId = resolvedSubmissionId,
                            studentId = studentId,
                            studentName = studentName,
                        ))
                    },
                    modifier = contentModifier
                )
            }
        }
    } else {
        when (state.resourceType) {
            ClassResourceType.ASSIGNMENT -> AssignmentSubmissionSheet(
                assignmentId = state.resourceId,
                topAppBar = resourceDetailsTopAppBar,
                navigateToCamera = { navigate(ResourceDetailsNavigation.NavigateToCamera) },
                modifier = Modifier.fillMaxSize(),
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