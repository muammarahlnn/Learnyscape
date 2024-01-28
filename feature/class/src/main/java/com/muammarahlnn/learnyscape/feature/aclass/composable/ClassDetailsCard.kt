package com.muammarahlnn.learnyscape.feature.aclass.composable

import androidx.compose.foundation.layout.Column
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
 * @File ClassDetailsCard, 27/01/2024 22.56
 */
@Composable
internal fun ClassDetailsCard(
    className: String,
    lecturers: List<String>,
    classSchedule: String,
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = className,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            lecturers.forEach { lecturer ->
                Text(
                    text = lecturer,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = classSchedule,
                style = MaterialTheme.typography.bodySmall,
                color =  MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}