package com.muammarahlnn.learnyscape.feature.studentwork

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.LoadingScreen
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.studentwork.composable.MissingStudentsCard
import com.muammarahlnn.learnyscape.feature.studentwork.composable.StudentSubmissionStatus
import com.muammarahlnn.learnyscape.feature.studentwork.composable.SubmittedStudentsCard

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentWorkContent, 22/02/2024 21.39
 */
enum class StudentWorkType {
    ASSIGNMENT, QUIZ
}

@Composable
fun StudentWorkContent(
    studentWorkType: StudentWorkType,
    resourceId: String,
    onSubmissionClick: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudentWorkViewModel = hiltViewModel(),
) {
    val (state, event) = use(viewModel)
    LaunchedEffect(Unit) {
        sequence {
            yield(StudentWorkContract.Event.SetStudentWorkType(studentWorkType))
            yield(StudentWorkContract.Event.SetResourceId(resourceId))
            yield(StudentWorkContract.Event.FetchStudentWorks)
        }.forEach(event)
    }

    StudentWorkContent(
        state = state,
        event = { event(it) },
        onSubmissionClick = onSubmissionClick,
        modifier = modifier,
    )
}

@Composable
private fun StudentWorkContent(
    state: StudentWorkContract.State,
    event: (StudentWorkContract.Event) -> Unit,
    onSubmissionClick: (String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val submittedText = stringResource(
        id = when (state.studentWorkType) {
            StudentWorkType.ASSIGNMENT -> R.string.turned_in
            StudentWorkType.QUIZ -> R.string.submitted
        }
    )

    when (state.uiState) {
        StudentWorkContract.UiState.Loading -> LoadingScreen(modifier = modifier)

        is StudentWorkContract.UiState.Error -> ErrorScreen(
            text = state.uiState.message,
            onRefresh = { event(StudentWorkContract.Event.FetchStudentWorks) },
            modifier = modifier,
        )

        StudentWorkContract.UiState.Success -> LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier,
        ) {
            item {
                StudentSubmissionStatus(
                    submittedText = submittedText,
                    submittedSubmissionSize = state.submittedSubmissions.size,
                    missingSubmissionSize = state.missingSubmissions.size,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (state.submittedSubmissions.isNotEmpty()) {
                item {
                    SubmittedStudentsCard(
                        submittedText = submittedText,
                        submittedStudents = state.submittedSubmissions,
                        onSubmissionClick = onSubmissionClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (state.missingSubmissions.isNotEmpty()) {
                item {
                    MissingStudentsCard(
                        missingStudents = state.missingSubmissions,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}