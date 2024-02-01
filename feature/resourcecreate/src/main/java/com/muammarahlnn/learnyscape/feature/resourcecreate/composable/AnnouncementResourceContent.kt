package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.feature.resourcecreate.ResourceCreateContract
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AnnouncementResourceContent, 26/12/2023 07.47
 */
@Composable
internal fun AnnouncementResourceContent(
    state: ResourceCreateContract.State,
    onDescriptionChange: (String) -> Unit,
    onAddAttachmentClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    onMoreVertAttachmentClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        DescriptionInputCard(
            resourceType = state.resourceType,
            description = state.description,
            onDescriptionChange = onDescriptionChange,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AttachmentsInputCard(
            attachments = state.attachments,
            onAddAttachmentClick = onAddAttachmentClick,
            onAttachmentClick = onAttachmentClick,
            onMoreVertAttachmentClick = onMoreVertAttachmentClick,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}