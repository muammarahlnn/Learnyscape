package com.muammarahlnn.learnyscape.feature.studentwork.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmittedStudentsCard, 23/02/2024 22.06
 */
@Composable
internal fun SubmittedStudentsCard(
    submittedText: String,
    submittedStudents: List<StudentWorkContract.StudentSubmissionState>,
    onSubmissionClick: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SubmissionsCard(
        title = submittedText,
        modifier = modifier,
    ) {
        submittedStudents.forEach { submission ->
            SubmissionItem(
                profilePicUiState = submission.profilePicUiState,
                name = submission.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        onSubmissionClick(
                            submission.id,
                            submission.userId,
                            submission.name,
                            submission.turnedInAt
                        )
                    }
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = submittedText,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 11.sp,
                        ),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                    Text(
                        text = submission.turnedInAt,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}