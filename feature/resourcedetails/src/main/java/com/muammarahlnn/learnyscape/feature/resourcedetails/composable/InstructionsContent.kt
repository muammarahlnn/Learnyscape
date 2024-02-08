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
internal data class InstructionsContentEvent(
    val onStartQuizButtonClick: () -> Unit,
    val onAttachmentClick: (File) -> Unit,
    val onRefresh: () -> Unit,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun InstructionsContent(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
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
    onStartQuizButtonClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isStudent = isStudent(LocalUserModel.current.role)
    val isQuiz = state.resourceType == ClassResourceType.QUIZ
    val showStickyBottomActionButton = isStudent && isQuiz

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
                        quizStartDate = state.startQuizDate,
                        quizEndDate = state.endQuizDate,
                        quizDuration = state.quizDuration,
                        quizType = state.quizType,
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
            if (isQuiz) {
                StartQuizButton(
                    onButtonClick = onStartQuizButtonClick,
                    onButtonGloballyPositioned = { layoutCoordinates ->
                        addWorkButtonHeight = with(localDensity) {
                            layoutCoordinates.size.height.toDp()
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}