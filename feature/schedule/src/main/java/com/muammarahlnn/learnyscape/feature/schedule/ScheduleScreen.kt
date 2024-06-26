package com.muammarahlnn.learnyscape.feature.schedule

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.ui.EmptyScreen
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.NoInternetScreen
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.getCurrentDate
import com.muammarahlnn.learnyscape.core.ui.util.getCurrentDay
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleContract.Event
import com.muammarahlnn.learnyscape.feature.schedule.ScheduleContract.State
import com.muammarahlnn.learnyscape.feature.schedule.composable.LoadingScheduleScreen
import com.muammarahlnn.learnyscape.feature.schedule.composable.TodayScheduleCalendar


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ScheduleScreen, 20/07/2023 22.04 by Muammar Ahlan Abimanyu
 */
@Composable
internal fun ScheduleRoute(
    controller: ScheduleController,
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            is ScheduleNavigation.NavigateToClass ->
                controller.navigateToClass(navigation.classId)
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        event(Event.FetchSchedules)
    }

    ScheduleScreen(
        state = state,
        refreshState = use(viewModel) { event(Event.FetchSchedules) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ScheduleScreen(
    state: State,
    refreshState: RefreshState,
    event: (Event) -> Unit,
    navigate: (ScheduleNavigation) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            ScheduleTopAppBar(
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ScheduleDateHeader()

                val contentModifier = Modifier.fillMaxSize()
                when (state) {
                    State.Loading -> {
                        LoadingScheduleScreen(
                            modifier = contentModifier
                        )
                    }

                    is State.Success -> {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            modifier = contentModifier
                                .nestedScroll(scrollBehavior.nestedScrollConnection)
                        ) {
                            item {
                                TodayScheduleCalendar(
                                    schedules = state.schedules,
                                    onScheduleClick = { navigate(ScheduleNavigation.NavigateToClass(it)) },
                                    modifier = modifier.wrapContentSize()
                                )
                            }
                        }
                    }

                    State.SuccessEmpty -> {
                        EmptyScreen(
                            text = stringResource(id = R.string.empty_schedule_text),
                            modifier = contentModifier
                        )
                    }


                    is State.NoInternet -> {
                        NoInternetScreen(
                            text = state.message,
                            onRefresh = { event(Event.FetchSchedules) },
                            modifier = contentModifier
                        )
                    }

                    is State.Error -> {
                        ErrorScreen(
                            text = state.message,
                            onRefresh = { event(Event.FetchSchedules) },
                            modifier = contentModifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleDateHeader(
    modifier: Modifier = Modifier,
) {
    BaseCard(
        shape = RoundedCornerShape(
            bottomStart = 16.dp,
            bottomEnd = 16.dp,
        ),
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp,
                ),
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = getCurrentDay(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = getCurrentDate(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.schedule),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}