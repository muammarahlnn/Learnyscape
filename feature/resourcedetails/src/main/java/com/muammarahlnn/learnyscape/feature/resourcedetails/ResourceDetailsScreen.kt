package com.muammarahlnn.learnyscape.feature.resourcedetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.model.ui.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.PostCard
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.LecturerOnlyComposable
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import java.io.File
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


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

    Scaffold(
        topBar = {
            ResourceDetailsTopAppBar(
                titleRes = state.resourceType.nameRes,
                onBackClick = { event(ResourceDetailsContract.Event.OnBackClick) },
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
                DetailPostCard(
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

@Composable
private fun DetailPostCard(
    resourceType: ClassResourceType,
    name: String,
    date: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    PostCard(
        classResourceType = resourceType,
        title = name,
        timePosted = date,
        caption = description,
        isCaptionOverflowed = false,
        modifier = modifier,
    )
}

@Composable
private fun AttachmentsCard(
    attachments: List<File>,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.attachments),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            attachments.forEach { attachment ->
                AttachmentItem(
                    attachment = attachment,
                    onAttachmentClick = onAttachmentClick,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun AttachmentItem(
    attachment: File,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.noRippleClickable {
            onAttachmentClick(attachment)
        }
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_document),
                    contentDescription = stringResource(id = R.string.document_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))


        Text(
            text = attachment.nameWithoutExtension,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Composable
private fun QuizDetailsCard(
    quizStartTime: String,
    quizDuration: String,
    quizType: QuizType,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.details),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play_arrow),
                    contentDescription = stringResource(id = R.string.start_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = quizStartTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_timer),
                    contentDescription = stringResource(id = R.string.timer_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = quizDuration,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_quiz_type),
                    contentDescription = stringResource(id = R.string.quiz_type_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        id = when (quizType) {
                            QuizType.MULTIPLE_CHOICE_QUESTIONS -> R.string.multiple_choice_questions
                            QuizType.PHOTO_ANSWER -> R.string.photo_answer
                        }
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResourceDetailsTopAppBar(
    titleRes: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = titleRes),
        colors = LearnyscapeTopAppbarDefaults.defaultTopAppBarColors(),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    painter = painterResource(
                        id = designSystemR.drawable.ic_arrow_back
                    ),
                    contentDescription = stringResource(
                        id = designSystemR.string.navigation_back_icon_description,
                    ),
                )
            }
        },
        actionsIcon = {
            LecturerOnlyComposable {
                Row {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = designSystemR.drawable.ic_edit),
                            contentDescription = stringResource(id = R.string.edit_resource)
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = designSystemR.drawable.ic_delete),
                            contentDescription = stringResource(id = R.string.delete_resource)
                        )
                    }
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun AddWorkButton(
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseActionButton(
        onButtonClick = onButtonClick,
        onButtonGloballyPositioned = onButtonGloballyPositioned,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = designSystemR.drawable.ic_add),
            contentDescription = stringResource(
                id = designSystemR.string.add_icon_description,
            )
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = stringResource(id = R.string.add_work))
    }
}

@Composable
private fun StartQuizButton(
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseActionButton(
        onButtonClick = onButtonClick,
        onButtonGloballyPositioned = onButtonGloballyPositioned,
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.start_quiz))
    }
}

@Composable
private fun BaseActionButton(
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
    buttonContent: @Composable RowScope.() -> Unit,
) {
    BaseCard(
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onButtonGloballyPositioned(coordinates)
            }
    ) {
        Button(
            onClick = onButtonClick,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 8.dp,
                ),
            content = buttonContent,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddWorkBottomSheet(
    onCameraActionClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        onDismissRequest = onDismiss,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.5f)
                    .clickable {
                        onCameraActionClick()
                    },
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_photo_camera_border),
                    contentDescription = stringResource(id = R.string.camera),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.camera),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f),
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_upload),
                    contentDescription = stringResource(id = R.string.upload_file),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.upload_file),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun StartQuizDialog(
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

// dummy quiz type that random generated
private val quizType by lazy {
    when ((0..1).random()) {
        0 -> QuizType.MULTIPLE_CHOICE_QUESTIONS
        else -> QuizType.PHOTO_ANSWER
    }
}