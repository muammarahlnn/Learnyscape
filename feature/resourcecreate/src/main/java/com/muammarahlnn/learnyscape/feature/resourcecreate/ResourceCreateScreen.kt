package com.muammarahlnn.learnyscape.feature.resourcecreate

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.model.data.QuizType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.uriToFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AddAttachmentBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AnnouncementResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AssignmentResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.CreatingResourceDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.ModuleResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.QuizQuestions
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.QuizResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.QuizTypeBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.RemoveAttachmentBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.SetDueDateDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.SetDurationDialog
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateScreen, 17/12/2023 02.17
 */
@Composable
internal fun ResourceCreateRoute(
    controller: ResourceCreateController,
    modifier: Modifier = Modifier,
    viewModel: ResourceCreateViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            ResourceCreateNavigation.NavigateBack ->
                controller.navigateBack()

            ResourceCreateNavigation.NavigateToCamera ->
                controller.navigateToCamera()
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(ResourceCreateContract.Event.OnGetCapturedPhoto)
        if (state.isEdit) {
            event(ResourceCreateContract.Event.FetchResourceDetails)
        }
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            event(
                ResourceCreateContract.Event.OnFileSelected(
                    uriToFile(
                        context = context,
                        selectedFileUri = it,
                    )
                )
            )
        }
    }

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            ResourceCreateContract.Effect.OpenFiles ->
                launcher.launch("*/*")

            is ResourceCreateContract.Effect.ShowToast ->
                Toast.makeText(context, effect.message,Toast.LENGTH_SHORT).show()

            is ResourceCreateContract.Effect.OpenAttachment ->
                openFile(context, effect.attachment)
        }
    }

    ResourceCreateScreen(
        state = state,
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier
    )
}

@Composable
private fun ResourceCreateScreen(
    state: ResourceCreateContract.State,
    event: (ResourceCreateContract.Event) -> Unit,
    navigate: (ResourceCreateNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.overlayComposableVisibility.addAttachmentBottomSheet) {
        AddAttachmentBottomSheet(
            onUploadFileClick = {
                event(ResourceCreateContract.Event.OnUploadFileClick)
            },
            onCameraClick = {
                event(ResourceCreateContract.Event.OnShowAddAttachmentBottomSheet(false))
                navigate(ResourceCreateNavigation.NavigateToCamera)
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnShowAddAttachmentBottomSheet(false))
            },
        )
    }
    
    if (state.overlayComposableVisibility.removeAttachmentBottomSheet) {
        RemoveAttachmentBottomSheet(
            onRemoveAttachment = {
                event(ResourceCreateContract.Event.OnRemoveAttachment)
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissRemoveAttachmentBottomSheet)
            },
        )
    }

    if (state.overlayComposableVisibility.setDueDateDialog) {
        SetDueDateDialog(
            state = state,
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissSetDueDateDialog)
            },
            onConfirm = { dueDate, dueTime ->
                event(ResourceCreateContract.Event.OnConfirmSetDueDate(dueDate, dueTime))
            },
        )
    }


    if (state.overlayComposableVisibility.quizTypeBottomSheet) {
        QuizTypeBottomSheet(
            onMultipleChoiceQuestionClick = {
                event(ResourceCreateContract.Event.OnSelectQuizTypeBottomSheetOption(QuizType.MULTIPLE_CHOICE))
            },
            onPhotoAnswerClick = {
                event(ResourceCreateContract.Event.OnSelectQuizTypeBottomSheetOption(QuizType.PHOTO_ANSWER))
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissQuizTypeBottomSheet)
            },
        )
    }

    if (state.overlayComposableVisibility.durationDialog) {
        SetDurationDialog(
            duration = state.duration,
            onConfirm = {
                event(ResourceCreateContract.Event.OnConfirmSetDurationDialog(it))
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissDurationDialog)
            }
        )
    }

    if (state.overlayComposableVisibility.creatingResourceDialog) {
        CreatingResourceDialog(
            state = state.creatingResourceDialogState,
            onConfirmSuccess = {
                event(ResourceCreateContract.Event.OnConfirmSuccessCreatingResourceDialog)
                navigate(ResourceCreateNavigation.NavigateBack)
            },
            onDismiss = { event(ResourceCreateContract.Event.OnDismissCreatingResourceDialog) }
        )
    }

    AnimatedContent(
        targetState = state.showQuestionsScreen,
        label = "ResourceCreateScreen AnimatedContent",
    ) { targetState ->
        if (!targetState) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                        )
                ) {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                        contentDescription = stringResource(
                            id = designSystemR.string.navigation_back_icon_description
                        ),
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                navigate(ResourceCreateNavigation.NavigateBack)
                            }
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        val text = if (state.isEdit) {
                            stringResource(id = R.string.edit)
                        } else {
                            stringResource(
                                id = when (state.resourceType) {
                                    ClassResourceType.ANNOUNCEMENT,
                                    ClassResourceType.MODULE -> R.string.post
                                    ClassResourceType.ASSIGNMENT -> R.string.assign
                                    ClassResourceType.QUIZ -> R.string.create
                                }
                            )
                        }
                        Text(
                            text = text,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 32.dp,
                                )
                                .clickable {
                                    event(
                                        if (!state.isEdit) ResourceCreateContract.Event.OnCreateResourceClick
                                        else ResourceCreateContract.Event.OnEditResourceClick
                                    )
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (state.isLoading) {
                    LoadingScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    when (state.resourceType) {
                        ClassResourceType.ANNOUNCEMENT -> AnnouncementResourceContent(
                            state = state,
                            onDescriptionChange = {
                                event(ResourceCreateContract.Event.OnDescriptionChange(it))
                            },
                            onAddAttachmentClick = {
                                event(ResourceCreateContract.Event.OnShowAddAttachmentBottomSheet(true))
                            },
                            onAttachmentClick = { attachment ->
                                event(ResourceCreateContract.Event.OnAttachmentClick(attachment))
                            },
                            onMoreVertAttachmentClick = {
                                event(ResourceCreateContract.Event.OnMoreVertAttachmentClick(it))
                            },
                        )

                        ClassResourceType.MODULE -> ModuleResourceContent(
                            state = state,
                            onTitleChange = {
                                event(ResourceCreateContract.Event.OnTitleChange(it))
                            },
                            onDescriptionChange = {
                                event(ResourceCreateContract.Event.OnDescriptionChange(it))
                            },
                            onAddAttachmentClick = {
                                event(ResourceCreateContract.Event.OnShowAddAttachmentBottomSheet(true))
                            },
                            onAttachmentClick = { attachment ->
                                event(ResourceCreateContract.Event.OnAttachmentClick(attachment))
                            },
                            onMoreVertAttachmentClick = {
                                event(ResourceCreateContract.Event.OnMoreVertAttachmentClick(it))
                            },
                        )

                        ClassResourceType.ASSIGNMENT -> AssignmentResourceContent(
                            state = state,
                            onTitleChange = {
                                event(ResourceCreateContract.Event.OnTitleChange(it))
                            },
                            onDescriptionChange = {
                                event(ResourceCreateContract.Event.OnDescriptionChange(it))
                            },
                            onAddAttachmentClick = {
                                event(ResourceCreateContract.Event.OnShowAddAttachmentBottomSheet(true))
                            },
                            onAttachmentClick = { attachment ->
                                event(ResourceCreateContract.Event.OnAttachmentClick(attachment))
                            },
                            onMoreVertAttachmentClick = {
                                event(ResourceCreateContract.Event.OnMoreVertAttachmentClick(it))
                            },
                            onDueDateClick = {
                                event(ResourceCreateContract.Event.OnDueDateClick(DueDateType.DUE_DATE))
                            }
                        )

                        ClassResourceType.QUIZ -> QuizResourceContent(
                            state = state,
                            onTitleChange = {
                                event(ResourceCreateContract.Event.OnTitleChange(it))
                            },
                            onDescriptionChange = {
                                event(ResourceCreateContract.Event.OnDescriptionChange(it))
                            },
                            onQuizTypeClick = {
                                event(ResourceCreateContract.Event.OnQuizTypeClick)
                            },
                            onQuestionsClick = {
                                event(ResourceCreateContract.Event.OnShowQuestionsScreen)
                            },
                            onStartDateClick = {
                                event(ResourceCreateContract.Event.OnDueDateClick(DueDateType.START_DATE))
                            },
                            onEndDateClick = {
                                event(ResourceCreateContract.Event.OnDueDateClick(DueDateType.END_DATE))
                            },
                            onDurationClick = {
                                event(ResourceCreateContract.Event.OnDurationClick)
                            }
                        )
                    }
                }
            }
        } else {
            val unfilledQuestionsMessage = stringResource(id = R.string.unfilled_questions_message)
            QuizQuestions(
                quizType = state.quizType,
                multipleChoiceQuestions = state.multipleChoiceQuestions,
                photoAnswerQuestions = state.photoAnswerQuestions,
                onCloseClick = {
                    event(ResourceCreateContract.Event.OnCloseQuestionsScreen)
                },
                onUnfilledQuestionsExists = {
                    event(ResourceCreateContract.Event.OnUnfilledQuestions(unfilledQuestionsMessage))
                },
                onSaveQuestions = { multipleChoiceQuestions, photoAnswerQuestions ->
                    event(
                        ResourceCreateContract.Event.OnSaveQuestions(
                            multipleChoiceQuestions,
                            photoAnswerQuestions
                        )
                    )
                },
            )
        }
    }
}
