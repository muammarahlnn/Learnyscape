package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R
import java.io.File
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR
/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionAttachment, 19/02/2024 19.42
 */
@Composable
internal fun SubmissionAttachment(
    index: Int,
    attachment: File,
    isLoading: Boolean,
    isTurnedIn: Boolean,
    onAttachmentClick: (File) -> Unit,
    onRemoveClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.alpha(if (isLoading) 0.15f else 1f)
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
            modifier = Modifier.clickable(
                enabled = !isLoading
            ) { onAttachmentClick(attachment) }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_document),
                    contentDescription = stringResource(id = designSystemR.string.attachment_icon_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                )

                if (!isTurnedIn) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(bottomStart = 8.dp)
                            )
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .align(Alignment.TopEnd)
                            .clickable(
                                enabled = !isLoading
                            ) { onRemoveClick(index) }
                            .padding(
                                vertical = 4.dp,
                                horizontal = 12.dp
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = designSystemR.drawable.ic_close),
                            contentDescription = stringResource(id = R.string.remove_attachment),
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = attachment.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}