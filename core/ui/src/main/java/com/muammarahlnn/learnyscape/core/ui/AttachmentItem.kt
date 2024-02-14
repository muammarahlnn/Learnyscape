package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import java.io.File
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AttachmentItem, 02/02/2024 22.34
 */
@Composable
fun AttachmentItem(
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
                    .height(100.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_document),
                    contentDescription = stringResource(id = designSystemR.string.attachment_icon_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center),
                )
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