package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.LocalUserModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.StudentOnlyComposable
import com.muammarahlnn.learnyscape.core.ui.util.isStudent
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File InstructionsContent, 29/01/2024 18.32
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun InstructionsContent(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
    onAddWorkButtonClick: () -> Unit,
    onStartQuizButtonClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullRefreshScreen(
        pullRefreshState = refreshState.pullRefreshState,
        refreshing = refreshState.refreshing,
        modifier = modifier,
    ) {
        val contentModifier = Modifier.fillMaxSize()
        when (state.uiState) {
            ResourceDetailsContract.UiState.Loading -> LoadingScreen(
                modifier = contentModifier,
            )

            ResourceDetailsContract.UiState.Success -> PullRefreshScreen(
                pullRefreshState = refreshState.pullRefreshState,
                refreshing = refreshState.refreshing,
                modifier = contentModifier,
            ) {
                InstructionsContent(
                    state = state,
                    onAddWorkButtonClick = onAddWorkButtonClick,
                    onStartQuizButtonClick = onStartQuizButtonClick,
                    onAttachmentClick = onAttachmentClick,
                )
            }

            is ResourceDetailsContract.UiState.Error -> ErrorScreen(
                text = state.uiState.message,
                onRefresh = onRefresh,
                modifier = contentModifier
            )
        }
    }
}

@Composable
private fun InstructionsContent(
    state: ResourceDetailsContract.State,
    onAddWorkButtonClick: () -> Unit,
    onStartQuizButtonClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    val user = LocalUserModel.current
    val isAssignment = state.resourceType == ClassResourceType.ASSIGNMENT
    val isQuiz = state.resourceType == ClassResourceType.QUIZ
    val showStickyBottomActionButton = (isAssignment || isQuiz) && isStudent(user.role)

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
                bottom = if (showStickyBottomActionButton) 0.dp else 16.dp,
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

            if (showStickyBottomActionButton) {
                item {
                    Spacer(modifier = Modifier.height(addWorkButtonHeight))
                }
            }
        }

        StudentOnlyComposable {
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
}