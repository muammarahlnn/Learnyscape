package com.muammarahlnn.learnyscape.feature.resourcecreate

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseBottomSheet
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.TransparentTextField
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.core.ui.util.uriToFile
import com.muammarahlnn.learnyscape.core.ui.util.use
import java.io.File
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
                ResourceCreateContract.Event.OnUploadFile(
                    uriToFile(
                        context = context,
                        selectedFileUri = it,
                    )
                )
            )
        }
    }

    ResourceCreateScreen(
        state = state,
        onCloseClick = onCloseClick,
        onDescriptionChange = { description ->
            event(ResourceCreateContract.Event.OnDescriptionChange(description))
        },
        onAddAttachmentClick = {
            event(ResourceCreateContract.Event.OnShowUploadAttachmentBottomSheet(true))
        },
        onUploadFileClick = {
            launcher.launch("*/*")
            event(ResourceCreateContract.Event.OnShowUploadAttachmentBottomSheet(false))
        },
        onCameraClick = {
            onCameraClick()
            event(ResourceCreateContract.Event.OnShowUploadAttachmentBottomSheet(false))
        },
        onDismissUploadAttachmentBottomSheet = {
            event(ResourceCreateContract.Event.OnShowUploadAttachmentBottomSheet(false))
        },
        modifier = modifier
    )
}

@Composable
private fun ResourceCreateScreen(
    state: ResourceCreateContract.State,
    onCloseClick: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddAttachmentClick: () -> Unit,
    onUploadFileClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismissUploadAttachmentBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.showUploadAttachmentBottomSheet) {
        AddAttachmentBottomSheet(
            onUploadFileClick = onUploadFileClick,
            onCameraClick = onCameraClick,
            onDismiss = onDismissUploadAttachmentBottomSheet,
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
                        onCloseClick()
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
            onDescriptionChange = onDescriptionChange,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AttachmentsInputCard(
            attachments = state.attachments,
            onAddAttachmentClick = onAddAttachmentClick,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun DescriptionInputCard(
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = R.drawable.ic_subject,
        iconDescriptionRes = R.string.description,
        modifier = modifier,
    ) {
        TransparentTextField(
            value = description,
            placeholderText = stringResource(id = R.string.description_input_placeholder),
            onValueChange = onDescriptionChange,
        )
    }
}

@Composable
private fun AttachmentsInputCard(
    attachments: List<File>,
    onAddAttachmentClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = R.drawable.ic_attachment,
        iconDescriptionRes = R.string.attachment,
        modifier = modifier.clickable {
            onAddAttachmentClick()
        },
    ) {
        if (attachments.isEmpty()) {
            Text(
                text = stringResource(id = R.string.attachment_input_placeholder),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        } else {
            Column {
                attachments.forEach { file ->
                    AttachmentItem(
                        file = file,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                
                Text(
                    text = stringResource(id = R.string.attachment_input_placeholder),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.noRippleClickable {
                        onAddAttachmentClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun AttachmentItem(
    file: File,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .size(
                        height = 36.dp,
                        width = 34.dp,
                    )
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_document),
                    contentDescription = stringResource(id = R.string.attachment),
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(5.dp),
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = file.nameWithoutExtension,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = stringResource(id = R.string.delete_file),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.padding(end = 32.dp)
        )
    }
}

@Composable
private fun InputCard(
    iconRes: Int,
    iconDescriptionRes: Int,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    BaseCard(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 24.dp,
                    horizontal = 16.dp,
                )
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = stringResource(id = iconDescriptionRes),
                tint = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.width(16.dp))

            content()
        }
    }
}

@Composable
private fun AddAttachmentBottomSheet(
    onUploadFileClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseBottomSheet(
        onDismiss = onDismiss,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .noRippleClickable {
                    onUploadFileClick()
                }
        ) {
            Icon(
                painter = painterResource(id = designSystemR.drawable.ic_upload),
                contentDescription = stringResource(id = R.string.upload_file),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.upload_file),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .noRippleClickable {
                    onCameraClick()
                }
        ) {
            Icon(
                painter = painterResource(id = designSystemR.drawable.ic_photo_camera_border),
                contentDescription = stringResource(id = R.string.take_a_photo),
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.take_a_photo),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}