package com.muammarahlnn.learnyscape.feature.assignment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.model.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.ClassResourceCard
import com.muammarahlnn.learnyscape.core.ui.ClassTopAppBar


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file AssignmentScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun AssignmentRoute(
    onAssignmentClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AssignmentScreen(
        onAssignmentClick = onAssignmentClick,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignmentScreen(
    onAssignmentClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier.fillMaxSize()) {
        ClassTopAppBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick
        )
        AssignmentContent(
            onAssignmentClick = onAssignmentClick,
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignmentContent(
    scrollBehavior: TopAppBarScrollBehavior,
    onAssignmentClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
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