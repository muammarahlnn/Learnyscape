package com.muammarahlnn.learnyscape.feature.assignmentsubmission

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.openFile
import com.muammarahlnn.learnyscape.core.ui.util.uriToFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable.OverlayDialogs
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable.SheetContent
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable.TurnInButton
import kotlinx.coroutines.launch

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentSubmissionSheet, 19/02/2024 18.46
 */
@Composable
fun AssignmentSubmissionSheet(
    assignmentId: String,
    isAssignmentDeleted: Boolean,
    topAppBar: @Composable () -> Unit,
    navigateToCamera: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AssignmentSubmissionViewModel = hiltViewModel(),
    content: @Composable (PaddingValues) -> Unit,
) {
    val (state, event) = use(viewModel)
    LaunchedEffect(Unit) {
        sequence {
            yield(AssignmentSubmissionContract.Event.SetAssignmentId(assignmentId))
            yield(AssignmentSubmissionContract.Event.FetchStudentSubmission)
        }.forEach { event(it) }
    }

    LaunchedEffect(isAssignmentDeleted) {
        event(AssignmentSubmissionContract.Event.SetIsAssignmentDeleted(isAssignmentDeleted))
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            event(
                AssignmentSubmissionContract.Event.OnFileSelected(
                    uriToFile(
                        context = context,
                        selectedFileUri = it
                    )
                )
            )
        }
    }

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is AssignmentSubmissionContract.Effect.ShowToast ->
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

            AssignmentSubmissionContract.Effect.OpenFiles ->
                launcher.launch("*/*")

            is AssignmentSubmissionContract.Effect.OpenAttachment ->
                openFile(context, effect.attachment)
        }
    }

    OverlayDialogs(
        overlayComposableVisibility = state.overlayComposableVisibility,
        attachmentSize = state.submission.attachments.size,
        navigateToCamera = navigateToCamera,
        event = event,
    )

    AssignmentSubmissionSheet(
        state = state,
        event = { event(it) },
        topAppBar = topAppBar,
        content = content,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignmentSubmissionSheet(
    state: AssignmentSubmissionContract.State,
    event: (AssignmentSubmissionContract.Event) -> Unit,
    topAppBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )
    )
    val coroutineScope = rememberCoroutineScope()

    val density  = LocalDensity.current
    var bottomPadding by remember { mutableStateOf(0.dp) }

    LaunchedEffect(state.isAssignmentDeleted) {
        if (state.isAssignmentDeleted) {
            scaffoldState.bottomSheetState.hide()
        }
    }

    Box(modifier = modifier) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShadowElevation = 8.dp,
            sheetShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
            sheetPeekHeight = when {
                state.submission.attachments.isEmpty() -> 160.dp
                state.submission.turnInStatus -> 180.dp
                else -> 240.dp
            },
            sheetContent = {
                SheetContent(
                    state = state,
                    sheetState = scaffoldState.bottomSheetState,
                    event = event,
                    onExpandSheet = {
                        coroutineScope.launch { scaffoldState.bottomSheetState.expand() }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = if (state.submission.turnInStatus) 16.dp else 16.dp + bottomPadding
                        )
                )
            },
            topBar = topAppBar,
            content = content,
        )

        if (!state.submission.turnInStatus && !state.isAssignmentDeleted) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        bottomPadding = with(density) {
                            layoutCoordinates.size.height.toDp()
                        }
                    }
            ) {
                val (actionTextResId, actionEvent) = when {
                    state.submission.attachments.isEmpty() ->
                        Pair(R.string.add_work) {
                            event(AssignmentSubmissionContract.Event.OnShowAddWorkBottomSheet(true))
                        }

                    else -> Pair(R.string.turn_in) {
                        event(AssignmentSubmissionContract.Event.OnShowTurnInDialog(true))
                    }
                }

                val isFetchAssignmentSubmissionLoading =
                    state.uiState is AssignmentSubmissionContract.UiState.Loading
                val loading = state.isTurnInSubmissionLoading || isFetchAssignmentSubmissionLoading

                TurnInButton(
                    actionText = stringResource(id = actionTextResId),
                    loading = loading,
                    onClick = actionEvent,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}