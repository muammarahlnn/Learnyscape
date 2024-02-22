package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionActionButton, 22/02/2024 01.33
 */
@Composable
internal fun SubmissionActionButton(
    actionText: String,
    loading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        ),
        enabled = !loading,
        modifier = modifier,
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                strokeWidth = 3.dp,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}