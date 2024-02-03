package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.AttachmentItem
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

            for (i in attachments.indices step 2) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AttachmentItem(
                        attachment = attachments[i],
                        onAttachmentClick = onAttachmentClick,
                        modifier = Modifier.weight(0.5f)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    if (i + 1 < attachments.size) {
                        AttachmentItem(
                            attachment = attachments[i + 1],
                            onAttachmentClick = onAttachmentClick,
                            modifier = Modifier.weight(0.5f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
//            attachments.forEach { attachment ->
//                AttachmentItem(
//                    attachment = attachment,
//                    onAttachmentClick = onAttachmentClick,
//                    modifier = Modifier.fillMaxWidth()
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
        }
    }
}