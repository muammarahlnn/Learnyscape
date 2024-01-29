package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AddWorkBottomSheet, 28/01/2024 15.33
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddWorkBottomSheet(
    onCameraActionClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = bottomSheetState,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        onDismissRequest = onDismiss,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.5f)
                    .clickable {
                        onCameraActionClick()
                    },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_photo_camera_border),
                    contentDescription = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.camera),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.camera),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.upload_file),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.upload_file),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}