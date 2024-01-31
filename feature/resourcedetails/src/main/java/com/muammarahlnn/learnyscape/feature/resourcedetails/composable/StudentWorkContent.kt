package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.feature.resourcedetails.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentWorkContent, 31/01/2024 16.48
 */
internal enum class StudentWorkType {
    ASSIGNMENT, QUIZ
}

@Composable
internal fun StudentWorkContent(
    studentWorkType: StudentWorkType,
    modifier: Modifier = Modifier,
) {
    val submittedText = stringResource(
        id = when (studentWorkType) {
            StudentWorkType.ASSIGNMENT -> R.string.turned_in
            StudentWorkType.QUIZ -> R.string.submitted
        }
    )

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        item {
            StudentSubmissionStatus(
                submittedText = submittedText,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            SubmittedStudentsCard(
                submittedText = submittedText,
                submittedStudents = listOf(
                    "Lorem ipsum dolor sit amet",
                    "Lorem ipsum dolor sit amet",
                    "Lorem ipsum dolor sit amet",
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            MissingStudentsCard(
                missingStudents = listOf(
                    "Lorem ipsum dolor sit amet",
                    "Lorem ipsum dolor sit amet",
                    "Lorem ipsum dolor sit amet",
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun StudentSubmissionStatus(
    submittedText: String,
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "21",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = submittedText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Divider(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column {
                Text(
                    text = "7",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(id = R.string.missing),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun SubmittedStudentsCard(
    submittedText: String,
    submittedStudents: List<String>,
    modifier: Modifier = Modifier,
) {
    SubmissionsCard(
        title = submittedText,
        modifier = modifier,
    ) {
        submittedStudents.forEach {
            SubmissionItem(
                name = it,
                modifier = Modifier.fillMaxWidth()
            ) {
                SubmittedText(submittedText)
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun MissingStudentsCard(
    missingStudents: List<String>,
    modifier: Modifier = Modifier,
) {
    SubmissionsCard(
        title = stringResource(id = R.string.missing),
        modifier = modifier,
    ) {
        missingStudents.forEach {
            SubmissionItem(
                name = it,
                modifier = Modifier.fillMaxWidth()
            ) {
                MissingText()
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun SubmissionsCard(
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

@Composable
private fun SubmissionItem(
    name: String,
    modifier: Modifier = Modifier,
    submissionStatusContent: @Composable () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PhotoProfileImage(
            uiState = PhotoProfileImageUiState.Success(null),
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )
        
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(12.dp))

        submissionStatusContent()
    }
}

@Composable
private fun SubmittedText(submittedText: String) {
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
            text = "at 21:12",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
            ),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun MissingText() {
    Text(
        text = stringResource(id = R.string.missing),
        style = MaterialTheme.typography.labelMedium.copy(
            fontSize = 11.sp,
        ),
        color = MaterialTheme.colorScheme.primary,
    )
}