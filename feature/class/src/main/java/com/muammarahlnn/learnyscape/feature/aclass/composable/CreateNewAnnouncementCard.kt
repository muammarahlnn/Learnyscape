package com.muammarahlnn.learnyscape.feature.aclass.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.aclass.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File CreateAnnouncementCard, 27/01/2024 23.02
 */
@Composable
internal fun CreateNewAnnouncementCard(
    profilePicUiState: PhotoProfileImageUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PhotoProfileImage(
                uiState = profilePicUiState,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.create_new_announcement_card_text),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                ),
                color = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}