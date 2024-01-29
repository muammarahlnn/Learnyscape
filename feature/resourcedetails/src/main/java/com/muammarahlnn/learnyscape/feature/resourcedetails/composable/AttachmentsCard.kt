package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcedetails.R
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AttachmentsCard, 28/01/2024 15.36
 */
@Composable
internal fun AttachmentsCard(
    attachments: List<File>,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.attachments),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 13.sp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            attachments.forEach { attachment ->
                AttachmentItem(
                    attachment = attachment,
                    onAttachmentClick = onAttachmentClick,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun AttachmentItem(
    attachment: File,
    onAttachmentClick: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.noRippleClickable {
            onAttachmentClick(attachment)
        }
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Icon(
                    painter = painterResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.drawable.ic_document),
                    contentDescription = stringResource(id = R.string.document_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))


        Text(
            text = attachment.nameWithoutExtension,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}