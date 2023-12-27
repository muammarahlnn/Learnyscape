package com.muammarahlnn.learnyscape.feature.assignment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ResourceClassScreen


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AssignmentScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun AssignmentRoute(
    onAssignmentClick: (Int) -> Unit,
    onCreateNewAssignmentClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AssignmentScreen(
        onAssignmentClick = onAssignmentClick,
        onCreateNewAssignmentClick = onCreateNewAssignmentClick,
        modifier = modifier,
    )
}

@Composable
private fun AssignmentScreen(
    onAssignmentClick: (Int) -> Unit,
    onCreateNewAssignmentClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResourceClassScreen(
        onCreateNewResourceClick = {
            onCreateNewAssignmentClick(ClassResourceType.ASSIGNMENT.ordinal)
        },
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(paddingValues),
        ) {
            repeat(20) {
                item {
                    ClassResourceCard(
                        classResourceType = ClassResourceType.ASSIGNMENT,
                        title = "Tugas Background Thread",
                        timeLabel = "Due 21 May 2023, 21:21",
                        onItemClick = onAssignmentClick,
                    )
                }
            }
        }
    }
}