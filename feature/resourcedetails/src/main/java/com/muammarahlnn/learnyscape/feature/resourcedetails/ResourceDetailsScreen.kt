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
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.AddWorkBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeleteResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeletingResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.InstructionsContent
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsPager
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsTopAppBar
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.StartQuizDialog


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ResourceDetailsScreen, 18/08/2023 00.48 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ResourceDetailsRoute(
    navigateBack: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToQuizSession: (Int, String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResourceDetailsViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(ResourceDetailsContract.Event.FetchResourceDetails)
    }

    val context = LocalContext.current
    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            ResourceDetailsContract.Effect.NavigateBack ->
                navigateBack()

            ResourceDetailsContract.Effect.NavigateToCamera ->
                navigateToCamera()

            is ResourceDetailsContract.Effect.NavigateToQuizSession ->
                navigateToQuizSession(it.quizDuration, it.quizName, it.quizDuration)

            is ResourceDetailsContract.Effect.OpenAttachment ->
                openFile(context, it.attachment)
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
            onDismiss = { event(ResourceDetailsContract.Event.OnDismissAddWorkBottomSheet) },
        )
    }

    if (state.overlayComposableVisibility.showStartQuizDialog) {
        StartQuizDialog(
            onConfirm = { quizTypeOrdinal, quizName, quizDuration ->
                event(
                    ResourceDetailsContract.Event.OnConfirmStartQuizDialog(
                        quizTypeOrdinal = quizTypeOrdinal,
                        quizName = quizName,
                        quizDuration = quizDuration,
                    )
                )
            },
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

    Scaffold(
        topBar = {
            ResourceDetailsTopAppBar(
                titleRes = state.resourceType.nameRes,
                onBackClick = { event(ResourceDetailsContract.Event.OnBackClick) },
                onDeleteClick = { event(ResourceDetailsContract.Event.OnDeleteClick) }
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        val screenModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

        when (state.resourceType) {
            ClassResourceType.ANNOUNCEMENT,
            ClassResourceType.MODULE -> InstructionsContent(
                state = state,
                refreshState = refreshState,
                onAddWorkButtonClick = { event(ResourceDetailsContract.Event.OnAddWorkButtonClick) },
                onStartQuizButtonClick = { event(ResourceDetailsContract.Event.OnStartQuizButtonClick) },
                onAttachmentClick = { event(ResourceDetailsContract.Event.OnAttachmentClick(it)) },
                onRefresh = { event(ResourceDetailsContract.Event.FetchResourceDetails) },
                modifier = screenModifier
            )

            ClassResourceType.ASSIGNMENT,
            ClassResourceType.QUIZ -> ResourceDetailsPager(
                state = state,
                refreshState = refreshState,
                onAddWorkButtonClick = { event(ResourceDetailsContract.Event.OnAddWorkButtonClick) },
                onStartQuizButtonClick = { event(ResourceDetailsContract.Event.OnStartQuizButtonClick) },
                onAttachmentClick = { event(ResourceDetailsContract.Event.OnAttachmentClick(it)) },
                onRefresh = { event(ResourceDetailsContract.Event.FetchResourceDetails) },
                modifier = screenModifier
            )
        }
    }
}