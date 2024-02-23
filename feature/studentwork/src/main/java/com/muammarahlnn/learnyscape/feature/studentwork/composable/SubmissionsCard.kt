package com.muammarahlnn.learnyscape.feature.studentwork.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionsCard, 23/02/2024 21.30
 */
@Composable
internal fun SubmissionsCard(
    title: String,
    modifier: Modifier = Modifier,
    submissionsContent: @Composable ColumnScope.() -> Unit,
) {
    BaseCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            submissionsContent()
        }
    }
}