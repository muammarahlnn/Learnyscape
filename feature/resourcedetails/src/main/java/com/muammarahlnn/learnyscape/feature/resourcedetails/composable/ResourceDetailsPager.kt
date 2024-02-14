package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.feature.resourcedetails.R
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract
import kotlinx.coroutines.launch
import java.io.File

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsPager, 29/01/2024 18.02
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ResourceDetailsPager(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
    onStartQuizButtonClick: () -> Unit,
    onAttachmentClick: (File) -> Unit,
    onRefreshInstructions: () -> Unit,
    onRefreshStudentWork: () -> Unit,
    onSubmissionClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerTabs = listOf(
        stringResource(id = R.string.instructions),
        stringResource(id = R.string.student_work),
    )
    val pagerState = rememberPagerState { pagerTabs.size }

    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            divider = {}, // remove default divider
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MaterialTheme.colorScheme.onSecondary,
                    height = 2.dp,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            },
            modifier = Modifier
                .shadow( // to create shadow elevation only
                    elevation = 8.dp,
                    spotColor = Color.Transparent,
                )
        ) {
            pagerTabs.forEachIndexed { index, tab ->
                Tab(
                    selected = index == pagerState.currentPage,
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.surfaceVariant,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(vertical = 6.dp),
                        )
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            val studentWorkType = when (state.resourceType) {
                ClassResourceType.ASSIGNMENT -> StudentWorkType.ASSIGNMENT
                ClassResourceType.QUIZ -> StudentWorkType.QUIZ
                else -> throw IllegalAccessException("Only assignment and quiz is expected")
            }

            when (pageIndex) {
                0 -> InstructionsContent(
                    state = state,
                    refreshState = refreshState,
                    onStartQuizButtonClick = onStartQuizButtonClick,
                    onAttachmentClick = onAttachmentClick,
                    onRefresh = onRefreshInstructions,
                    modifier = Modifier.fillMaxSize()
                )

                1 -> StudentWorkContent(
                    state = state,
                    studentWorkType  = studentWorkType,
                    onRefresh = onRefreshStudentWork,
                    onSubmissionClick = onSubmissionClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}