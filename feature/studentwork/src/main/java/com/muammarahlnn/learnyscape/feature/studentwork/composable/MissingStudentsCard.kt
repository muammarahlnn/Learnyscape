package com.muammarahlnn.learnyscape.feature.studentwork.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.feature.studentwork.R
import com.muammarahlnn.learnyscape.feature.studentwork.StudentWorkContract

/**
 * @Author Muammar Ahlan Abimanyu
 * @File MissingStudentsCard, 23/02/2024 22.07
 */
@Composable
internal fun MissingStudentsCard(
    missingStudents: List<StudentWorkContract.StudentSubmissionState>,
    modifier: Modifier = Modifier,
) {
    SubmissionsCard(
        title = stringResource(id = R.string.missing),
        modifier = modifier,
    ) {
        missingStudents.forEach { submission ->
            SubmissionItem(
                profilePicUiState = submission.profilePicUiState,
                name = submission.name,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.missing),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 11.sp,
                    ),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}