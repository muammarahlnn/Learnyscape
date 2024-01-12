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

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AssignmentResourceContent, 27/12/2023 00.22
 */
@Composable
internal fun AssignmentResourceContent(
    state: ResourceCreateContract.State,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddAttachmentCLick: () -> Unit,
    onMoreVertAttachmentClick: (Int) -> Unit,
    onDueDateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TitleInputCard(
            resourceType = state.resourceType,
            title = state.title,
            onTitleChange = onTitleChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        DescriptionInputCard(
            resourceType = state.resourceType,
            description = state.description,
            onDescriptionChange = onDescriptionChange,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AttachmentsInputCard(
            attachments = state.attachments,
            onAddAttachmentClick = onAddAttachmentCLick,
            onMoreVertAttachmentClick = onMoreVertAttachmentClick,
        )

        Spacer(modifier = Modifier.height(16.dp))

        DueDateInputCard(
            date = state.dueDate.date,
            time = state.dueDate.time,
            onDueDateClick = onDueDateClick
        )
    }
}