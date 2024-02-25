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
import com.muammarahlnn.learnyscape.core.model.data.ClassDetailsModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassDetailsCard, 27/01/2024 22.56
 */
@Composable
internal fun ClassDetailsCard(
    classDetails: ClassDetailsModel,
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = classDetails.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )

            classDetails.lecturers.forEach { lecturer ->
                Text(
                    text = lecturer.fullName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val scheduleText = String.format(
                "%s, %02d:%02d - %02d:%02d",
                classDetails.day.displayedText,
                classDetails.startTime.hour, classDetails.startTime.minute,
                classDetails.endTime.hour, classDetails.endTime.minute,
            )
            Text(
                text = scheduleText,
                style = MaterialTheme.typography.bodySmall,
                color =  MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}