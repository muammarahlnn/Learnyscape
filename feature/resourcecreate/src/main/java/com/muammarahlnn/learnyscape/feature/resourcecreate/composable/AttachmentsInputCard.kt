package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import java.io.File
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AttachmentsInputCard, 25/12/2023 04.24
 */
@Composable
internal fun AttachmentsInputCard(
    attachments: List<File>,
    onAddAttachmentClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    onMoreVertAttachmentClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = designSystemR.drawable.ic_attachment,
        iconDescriptionRes = R.string.attachment,
        modifier = modifier,
    ) {
        AnimatedContent(
            targetState = attachments.isEmpty(),
            label = "Attachments AnimatedContent"
        ) { targetState ->
            if (targetState) {
                Text(
                    text = stringResource(id = R.string.attachment_input_placeholder),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .noRippleClickable {
                            onAddAttachmentClick()
                        }
                    ,
                )
            } else {
                Column(
                    modifier = Modifier.animateContentSize()
                ) {
                    attachments.forEachIndexed { index, file ->
                        AttachmentItem(
                            file = file,
                            onAttachmentClick = { attachment ->
                                onAttachmentClick(attachment)
                            },
                            onMoreVertClick = {
                                onMoreVertAttachmentClick(index)
                            },
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
}

@Composable
private fun AttachmentItem(
    file: File,
    onAttachmentClick: (File) -> Unit,
    onMoreVertClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onAttachmentClick(file) }
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
                text = file.name,
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
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {
                        onMoreVertClick()
                    },
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