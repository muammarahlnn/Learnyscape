package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.AddWorkBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.AddWorkButton
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.AttachmentsCard
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeleteResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.DeletingResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.QuizDetailsCard
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsCard
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.ResourceDetailsTopAppBar
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.StartQuizButton
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.StartQuizDialog
import com.muammarahlnn.learnyscape.feature.resourcedetails.composable.quizType
import java.io.File


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

@OptIn(ExperimentalMaterialApi::class)
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
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            val contentModifier = Modifier.fillMaxSize()
            when (state.uiState) {
                ResourceDetailsContract.UiState.Loading -> LoadingScreen(
                    modifier = contentModifier,
                )

                ResourceDetailsContract.UiState.Success -> {
                    ResourceDetailsContent(
                        state = state,
                        onAddWorkButtonClick = { event(ResourceDetailsContract.Event.OnAddWorkButtonClick) },
                        onStartQuizButtonClick = { event(ResourceDetailsContract.Event.OnStartQuizButtonClick) },
                        onAttachmentClick = { event(ResourceDetailsContract.Event.OnAttachmentClick(it)) }
                    )
                }

                is ResourceDetailsContract.UiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(ResourceDetailsContract.Event.FetchResourceDetails) }
                )
            }
        }
    }
}

@Composable
private fun ResourceDetailsContent(
    state: ResourceDetailsContract.State,
    onAddWorkButtonClick: () -> Unit,
    onStartQuizButtonClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isAssignment = state.resourceType == ClassResourceType.ASSIGNMENT
    val isQuiz = state.resourceType == ClassResourceType.QUIZ

    val localDensity = LocalDensity.current
    Box(modifier = modifier.fillMaxSize()) {
        var addWorkButtonHeight by remember {
            mutableStateOf(0.dp)
        }

        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = if (isAssignment || isQuiz) 0.dp else 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                ResourceDetailsCard(
                    resourceType = state.resourceType,
                    name = state.name,
                    date = state.date,
                    description = state.description,
                )
            }

            item {
                if (isQuiz) {
                    QuizDetailsCard(
                        quizStartTime = "12 August 2023, 11:12",
                        quizDuration = "10 Minutes",
                        quizType = quizType
                    )
                } else {
                    AttachmentsCard(
                        attachments = state.attachments,
                        onAttachmentClick = onAttachmentClick,
                    )
                }
            }

            if (isAssignment || isQuiz) {
                item {
                    Spacer(modifier = Modifier.height(addWorkButtonHeight))
                }
            }
        }

        val actionButtonModifier = Modifier.align(Alignment.BottomCenter)
        val onButtonGloballyPositioned: (LayoutCoordinates) -> Unit = { coordinates ->
            addWorkButtonHeight = with(localDensity) {
                coordinates.size.height.toDp()
            }
        }
        when {
            isAssignment -> {
                AddWorkButton(
                    onButtonClick = onAddWorkButtonClick,
                    onButtonGloballyPositioned = onButtonGloballyPositioned,
                    modifier = actionButtonModifier,
                )
            }

            isQuiz -> {
                StartQuizButton(
                    onButtonClick = onStartQuizButtonClick,
                    onButtonGloballyPositioned = onButtonGloballyPositioned,
                    modifier = actionButtonModifier,
                )
            }
        }
    }
}