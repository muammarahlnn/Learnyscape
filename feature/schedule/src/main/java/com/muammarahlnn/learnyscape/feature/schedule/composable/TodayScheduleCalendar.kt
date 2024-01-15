package com.muammarahlnn.learnyscape.feature.schedule.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel
import com.muammarahlnn.learnyscape.feature.schedule.R
import com.muammarahlnn.learnyscape.feature.schedule.composable.TodayScheduleCalendarScope.scheduleClassCard
import kotlinx.datetime.LocalTime
import kotlin.math.roundToInt

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file TodayScheduleCalendar, 20/09/2023 15.03 by Muammar Ahlan Abimanyu
 */

private const val START_HOUR = 6
private const val END_HOUR = 23
private val scheduleHours = START_HOUR..END_HOUR

@Composable
internal fun TodayScheduleCalendar(
    schedules: List<ScheduleModel>,
    onScheduleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hourLabels = @Composable {
        scheduleHours.forEach { hour ->
            HourLabel(hour = hour)
        }
    }

    val scheduleClasses = @Composable {
        schedules.forEach { schedule ->
            ScheduleClassCard(
                classId = schedule.id,
                className = schedule.className,
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                onScheduleClick = onScheduleClick,
                modifier = Modifier.scheduleClassCard(
                    startTime = schedule.startTime,
                    endTime = schedule.endTime,
                )
            )
        }
    }

    Layout(
        contents = listOf(hourLabels, scheduleClasses),
        modifier = modifier,
    ) { (hourLabelMeasurables, scheduleClassMeasurables),
        constraints ->
        var totalHeight = 0
        val hourLabelPlaceables = hourLabelMeasurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            totalHeight += placeable.height
            placeable
        }
        val hourLabelHeight = hourLabelPlaceables.first().height
        val hourLabelWidth = hourLabelPlaceables.first().width

        val spacerWidth = 16.dp.roundToPx()
        val scheduleClassWidth = constraints.maxWidth - hourLabelWidth - spacerWidth
        val scheduleClassPlaceables = scheduleClassMeasurables.map { measurable ->
            val scheduleClassParentData = measurable.parentData as TodayScheduleCalendarParentData
            val duration = scheduleClassParentData.durationInMinutes
            val scheduleHeight = (duration / 60f * hourLabelHeight).roundToInt()

            val placeable = measurable.measure(
                constraints.copy(
                    minWidth = scheduleClassWidth,
                    maxWidth = scheduleClassWidth,
                    minHeight = scheduleHeight,
                    maxHeight = scheduleHeight
                )
            )
            placeable
        }

        layout(
            width = constraints.maxWidth,
            height = totalHeight,
        ) {
            val xHourLabel = 0
            var yHourLabel = 0
            hourLabelPlaceables.forEach {hourLabelPlaceable ->
                hourLabelPlaceable.place(
                    x = xHourLabel,
                    y = yHourLabel
                )
                yHourLabel += hourLabelPlaceable.height
            }

            val xScheduleClass = hourLabelWidth + spacerWidth
            scheduleClassPlaceables.forEach { scheduleClassPlaceable ->
                val scheduleClassParentData = scheduleClassPlaceable.parentData as TodayScheduleCalendarParentData
                val startTimeOffset = scheduleClassParentData.startTimeOffset
                val yScheduleClass = (startTimeOffset / 60f * hourLabelHeight).roundToInt()
                scheduleClassPlaceable.place(
                    x = xScheduleClass,
                    y = yScheduleClass
                )
            }
        }
    }
}

@Composable
private fun HourLabel(hour: Int) {
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
    classId: String,
    className: String,
    startTime: LocalTime,
    endTime: LocalTime,
    onScheduleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onScheduleClick(classId)
            },
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxSize()
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

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                val canvasSize = 50.dp
                val primaryColor = MaterialTheme.colorScheme.primary
                Canvas(
                    modifier = Modifier.size(canvasSize),
                    onDraw = {
                        val size = canvasSize.toPx()
                        val trianglePath = Path().apply {
                            moveTo(size, 0f)
                            lineTo(size, size)
                            lineTo(0f, size)
                        }
                        drawPath(
                            path = trianglePath,
                            color = primaryColor,
                        )
                    }
                )
            }
        }
    }
}

@LayoutScopeMarker
@Immutable
private object TodayScheduleCalendarScope {

    @Stable
    fun Modifier.scheduleClassCard(
        startTime: LocalTime,
        endTime: LocalTime
    ): Modifier {
        // Calculate class duration in minutes
        val start = (startTime.hour * 60) + startTime.minute
        val end = (endTime.hour * 60) + endTime.minute
        val duration = end - start

        // Subtracted by 6 because the schedule hour is start from 06:00
        val startTimeOffsetHour = startTime.hour - scheduleHours.first
        val totalStartTimeOffset = (startTimeOffsetHour * 60) + startTime.minute

        return then(
            TodayScheduleCalendarParentData(
                durationInMinutes = duration,
                startTimeOffset = totalStartTimeOffset,
            )
        )
    }
}

private class TodayScheduleCalendarParentData(
    val durationInMinutes: Int,
    val startTimeOffset: Int,
) : ParentDataModifier {

    override fun Density.modifyParentData(parentData: Any?) =
        this@TodayScheduleCalendarParentData

}