package com.muammarahlnn.learnyscape.feature.resourcecreate

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
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.uriToFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AddAttachmentBottomSheet
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.AttachmentsInputCard
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.DescriptionInputCard
import com.muammarahlnn.learnyscape.feature.resourcecreate.composable.RemoveAttachmentBottomSheet
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

        DescriptionInputCard(
            description = state.description,
            onDescriptionChange = {
                event(ResourceCreateContract.Event.OnDescriptionChange(it))
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AttachmentsInputCard(
            attachments = state.attachments,
            onAddAttachmentClick = {
                event(ResourceCreateContract.Event.OnAddAttachmentClick)
            },
            onMoreVertAttachmentClick = { attachmentIndex ->
                event(ResourceCreateContract.Event.OnMoreVertAttachmentClick(attachmentIndex))
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
