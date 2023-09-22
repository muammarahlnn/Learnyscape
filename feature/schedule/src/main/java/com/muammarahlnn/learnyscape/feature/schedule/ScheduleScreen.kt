package com.muammarahlnn.learnyscape.feature.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.defaultTopAppBarColors
import kotlinx.datetime.LocalTime


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ScheduleScreen, 20/07/2023 22.04 by Muammar Ahlan Abimanyu
 */
@Composable
internal fun ScheduleRoute(
    modifier: Modifier = Modifier
) {
    ScheduleScreen(
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleScreen(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier) {
        ScheduleTopAppBar(
            scrollBehavior = scrollBehavior
        )
        ScheduleDateHeader()
        ScheduleContent(
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleContent(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        item {
            TodayScheduleCalendar(
                hourLabel = { hour ->
                    HourLabel(hour = hour)
                },
                scheduleClass = { scheduleClass ->
                    ScheduleClassCard(
                        className = scheduleClass.className,
                        startTime = scheduleClass.startTime,
                        endTime = scheduleClass.endTime,
                        modifier = Modifier.scheduleClassCard(
                            startTime = scheduleClass.startTime,
                            endTime = scheduleClass.endTime,
                        )
                    )
                },
                modifier = modifier.wrapContentSize()
            )
        }
    }
}

@Composable
private fun ScheduleDateHeader(
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(
            bottomStart = 10.dp,
            bottomEnd = 10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Monday",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "18 September 2023",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


@Composable
fun HourLabel(hour: Int) {
    val formattedHour = String.format("%02d:00", hour)
    Text(
        text = formattedHour,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.End,
        modifier = Modifier.height(100.dp)
    )
}

@Composable
private fun ScheduleClassCard(
    className: String,
    startTime: LocalTime,
    endTime: LocalTime,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
        ) {
            Text(
                text = className,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
            val formatHour = "%02d:%02d"
            val formattedStartTime = String.format(formatHour, startTime.hour, startTime.minute)
            val formattedEndTime = String.format(formatHour, endTime.hour, endTime.minute)
            Text(
                text = stringResource(
                    id = R.string.class_range_time,
                    formattedStartTime, formattedEndTime,
                ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = R.string.schedule,
        scrollBehavior = scrollBehavior,
        colors = defaultTopAppBarColors(),
        modifier = modifier,
    )
}