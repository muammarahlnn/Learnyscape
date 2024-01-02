package com.muammarahlnn.learnyscape.feature.resourcecreate

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.uriToFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AddAttachmentBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AnnouncementResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AssignmentResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDatePickerDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueDateType
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DueTimePickerDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.ModuleResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.QuizResourceContent
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.QuizTypeBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.RemoveAttachmentBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.SetDueDateDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.SetDurationDialog
import java.time.LocalDate
import java.time.LocalTime
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceCreateScreen, 17/12/2023 02.17
 */
@Composable
internal fun ResourceCreateRoute(
    onCloseClick: () -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ResourceCreateViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
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

    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            ResourceCreateContract.Effect.CloseScreen -> onCloseClick()
            ResourceCreateContract.Effect.OpenCamera -> onCameraClick()
            ResourceCreateContract.Effect.OpenFiles -> launcher.launch("*/*")
            is ResourceCreateContract.Effect.ShowToast ->
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
        }
    }

    ResourceCreateScreen(
        state = state,
        event = { event(it) },
        modifier = modifier
    )
}

@Composable
private fun ResourceCreateScreen(
    state: ResourceCreateContract.State,
    event: (ResourceCreateContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.overlayComposableVisibility.addAttachmentBottomSheet) {
        AddAttachmentBottomSheet(
            onUploadFileClick = {
                event(ResourceCreateContract.Event.OnUploadFileClick)
            },
            onCameraClick = {
                event(ResourceCreateContract.Event.OnCameraClick)
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissUploadAttachmentBottomSheet)
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
            onConfirm = {
                event(ResourceCreateContract.Event.OnConfirmSetDueDate)
            },
            onSetDateClick = {
                event(ResourceCreateContract.Event.OnSetDateClick)
            },
            onSetTimeClick = {
                event(ResourceCreateContract.Event.OnSetTimeClick)
            }
        )
    }

    if (state.overlayComposableVisibility.dueDatePickerDialog) {
        DueDatePickerDialog(
            date = state.dueDate.date ?: LocalDate.now(),
            onConfirm = {
                event(ResourceCreateContract.Event.OnConfirmPickDate(it))
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissDueDatePickerDialog)
            },
        )
    }

    if (state.overlayComposableVisibility.dueTimePickerDialog) {
        DueTimePickerDialog(
            time = state.dueDate.time ?: LocalTime.now(),
            onConfirm = {
                event(ResourceCreateContract.Event.OnConfirmPickTime(it))
            },
            onDismiss = {
                event(ResourceCreateContract.Event.OnDismissDueTimePickerDialog)
            }
        )
    }

    if (state.overlayComposableVisibility.quizTypeBottomSheet) {
        QuizTypeBottomSheet(
            onMultipleChoiceQuestionClick = {
                event(ResourceCreateContract.Event.OnSelectQuizTypeBottomSheetOption(QuizType.MCQ))
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
                painter = painterResource(id = designSystemR.drawable.ic_close),
                contentDescription = stringResource(
                    id = designSystemR.string.navigation_close_icon_description
                ),
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        event(ResourceCreateContract.Event.OnCloseClick)
                    }
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(
                        vertical = 8.dp,
                        horizontal = 32.dp,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (state.resourceType) {
            ClassResourceType.ANNOUNCEMENT -> AnnouncementResourceContent(
                state = state,
                onDescriptionChange = {
                    event(ResourceCreateContract.Event.OnDescriptionChange(it))
                },
                onAddAttachmentCLick = {
                    event(ResourceCreateContract.Event.OnAddAttachmentClick)
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
                onAddAttachmentCLick = {
                    event(ResourceCreateContract.Event.OnAddAttachmentClick)
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
                onAddAttachmentCLick = {
                    event(ResourceCreateContract.Event.OnAddAttachmentClick)
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
