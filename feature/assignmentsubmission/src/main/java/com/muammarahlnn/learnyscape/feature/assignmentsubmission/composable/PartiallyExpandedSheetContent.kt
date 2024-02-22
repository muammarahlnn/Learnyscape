package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR
/**
 * @Author Muammar Ahlan Abimanyu
 * @File PartiallyExpandedSheetContent, 19/02/2024 19.46
 */
@Composable
internal fun PartiallyExpandedSheetContent(
    attachmentsSize: Int,
    onSeeWorkClick: () -> Unit,
) {
    if (attachmentsSize > 0) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .fillMaxWidth()
                    .clickable { onSeeWorkClick() }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_attachment),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (attachmentsSize > 1) {
                        stringResource(id = R.string.plural_attachment, attachmentsSize)
                    } else stringResource(id = R.string.singular_attachment),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}