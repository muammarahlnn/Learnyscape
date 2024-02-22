package com.muammarahlnn.learnyscape.feature.assignmentsubmission.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.AssignmentSubmissionContract
import com.muammarahlnn.learnyscape.feature.assignmentsubmission.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SheetContent, 22/02/2024 00.36
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SheetContent(
    state: AssignmentSubmissionContract.State,
    sheetState: SheetState,
    event: (AssignmentSubmissionContract.Event) -> Unit,
    onExpandSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.your_work),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
            ),
        )

        when (sheetState.currentValue) {
            SheetValue.PartiallyExpanded -> PartiallyExpandedSheetContent(
                attachmentsSize = state.submission.attachments.size,
                onSeeWorkClick = onExpandSheet,
            )

            SheetValue.Expanded -> ExpandedSheetContent(
                state = state,
                event = event,
                modifier = Modifier.weight(1f)
            )

            SheetValue.Hidden -> Unit
        }
    }
}