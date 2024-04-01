package com.muammarahlnn.submissiondetails

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.data.StudentQuizAnswerModel
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.learnyscape.core.ui.AttachmentItem
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import java.io.File
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsScreen, 02/02/2024 18.27
 */
@Composable
internal fun SubmissionDetailsRoute(
    controller: SubmissionDetailsController,
    modifier: Modifier = Modifier,
    viewModel: SubmissionDetailsViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            SubmissionDetailsNavigation.NavigateBack ->
                controller.navigateBack()
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(SubmissionDetailsContract.Event.FetchSubmissionDetails)
    }

    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is SubmissionDetailsContract.Effect.OpenAttachment ->
                openFile(context, effect.attachment)
        }
    }

    SubmissionDetailsScreen(
        state = state,
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@Composable
private fun SubmissionDetailsScreen(
    state: SubmissionDetailsContract.State,
    event: (SubmissionDetailsContract.Event) -> Unit,
    navigate: (SubmissionDetailsNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .size(32.dp)
                .clickable { navigate(SubmissionDetailsNavigation.NavigateBack) }
        ) {
            Icon(
                painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                contentDescription = stringResource(
                    id = designSystemR.string.navigation_back_icon_description
                ),
                modifier = Modifier.padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state.uiState) {
            SubmissionDetailsContract.UiState.Loading -> LoadingScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            is SubmissionDetailsContract.UiState.Error -> ErrorScreen(
                text = state.uiState.message,
                onRefresh = { event(SubmissionDetailsContract.Event.FetchSubmissionDetails) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            SubmissionDetailsContract.UiState.Success -> when (state.submissionType) {
                SubmissionType.ASSIGNMENT -> StudentSubmissionCard(
                    profilePicUiState = state.profilePicUiState,
                    studentName = state.assignmentSubmission.studentName,
                    turnedInAt = state.turnedInAt,
                    attachments = state.assignmentSubmission.attachments,
                    onAttachmentClick = { event(SubmissionDetailsContract.Event.OnAttachmentClick(it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                SubmissionType.QUIZ -> StudentQuizAnswersContent(
                    profilePicUiState = state.profilePicUiState,
                    studentName = state.studentName,
                    quizAnswers = state.quizAnswers,
                    turnedInAt = state.turnedInAt,
                )
            }
        }
    }
}

@Composable
private fun StudentSubmissionCard(
    profilePicUiState: PhotoProfileImageUiState,
    studentName: String,
    turnedInAt: String,
    attachments: List<File>,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            StudentSubmissionDetailsRow(
                profilePicUiState = profilePicUiState,
                studentName = studentName,
                turnedInAt = turnedInAt,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            for (i in attachments.indices step 2) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AttachmentItem(
                        attachment = attachments[i],
                        onAttachmentClick = onAttachmentClick,
                        modifier = Modifier.weight(0.5f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    if (i + 1 < attachments.size) {
                        AttachmentItem(
                            attachment = attachments[i + 1],
                            onAttachmentClick = onAttachmentClick,
                            modifier = Modifier.weight(0.5f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun StudentQuizAnswersContent(
    profilePicUiState: PhotoProfileImageUiState,
    studentName: String,
    turnedInAt: String,
    quizAnswers: List<StudentQuizAnswerModel>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BaseCard(modifier = modifier) {
            StudentSubmissionDetailsRow(
                profilePicUiState = profilePicUiState,
                studentName = studentName,
                turnedInAt = turnedInAt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultipleChoiceAnswers(
            quizAnswers = quizAnswers,
        )
    }
}

@Composable
private fun StudentSubmissionDetailsRow(
    profilePicUiState: PhotoProfileImageUiState,
    studentName: String,
    turnedInAt: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        PhotoProfileImage(
            uiState = profilePicUiState,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = studentName,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = turnedInAt,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
private fun MultipleChoiceAnswers(
    quizAnswers: List<StudentQuizAnswerModel>,
) {
    Column {
        quizAnswers.forEachIndexed { index, answer ->
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .clip(CircleShape)
                        .size(30.dp)
                        .padding(5.dp)
                ) {
                    Text(
                        text = "${index + 1}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BaseCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = answer.question,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(10.dp))

                    listOf(
                        answer.optionA,
                        answer.optionB,
                        answer.optionC,
                        answer.optionD,
                        answer.optionE,
                    ).forEach { option ->
                        val isSolution = option.option == answer.solution
                        val (backgroundColor, textColor) = if (isSolution) {
                            MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.background to MaterialTheme.colorScheme.onBackground
                        }

                        BaseCard(
                            backgroundColor = backgroundColor,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "${option.option}.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = textColor,
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = option.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = textColor,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}