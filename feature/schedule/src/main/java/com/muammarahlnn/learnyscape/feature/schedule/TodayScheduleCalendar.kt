package com.muammarahlnn.learnyscape.feature.schedule

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
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
fun TodayScheduleCalendar(
    hourLabel: @Composable (Int) -> Unit,
    scheduleClass: @Composable TodayScheduleCalendarScope.(ScheduleClass) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hourLabels = @Composable {
        scheduleHours.forEach { hour ->
            hourLabel(hour)
        }
    }

    val scheduleClasses = @Composable {
        generateScheduleClasses().forEach { scheduleClass ->
            TodayScheduleCalendarScope.scheduleClass(scheduleClass)
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

private fun generateScheduleClasses(): List<ScheduleClass> =
    listOf(
        ScheduleClass(
            className = "Pemrograman Mobile A",
            startTime = LocalTime(
                hour = 6,
                minute = 0
            ),
            endTime = LocalTime(
                hour = 8,
                minute = 0
            ),
        ),
        ScheduleClass(
            className = "Pengantar Pemrograman A",
            startTime = LocalTime(
                hour = 8,
                minute = 30
            ),
            endTime = LocalTime(
                hour = 10,
                minute = 30
            ),
        ),
        ScheduleClass(
            className = "Sistem Basis Data B",
            startTime = LocalTime(
                hour = 11,
                minute = 10
            ),
            endTime = LocalTime(
                hour = 12,
                minute = 10
            ),
        ),
        ScheduleClass(
            className = "Pemrograman Web C",
            startTime = LocalTime(
                hour = 13,
                minute = 30
            ),
            endTime = LocalTime(
                hour = 14,
                minute = 30
            ),
        ),
        ScheduleClass(
            className = "Pemrograman Web C",
            startTime = LocalTime(
                hour = 15,
                minute = 15
            ),
            endTime = LocalTime(
                hour = 16,
                minute = 45
            ),
        ),
        ScheduleClass(
            className = "Pemrograman Web C",
            startTime = LocalTime(
                hour = 17,
                minute = 45
            ),
            endTime = LocalTime(
                hour = 19,
                minute = 15
            ),
        ),
        ScheduleClass(
            className = "Machine Learning A",
            startTime = LocalTime(
                hour = 20,
                minute = 0
            ),
            endTime = LocalTime(
                hour = 23,
                minute = 0
            ),
        ),
    )

data class ScheduleClass(
    val className: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
)

@LayoutScopeMarker
@Immutable
object TodayScheduleCalendarScope {

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

class TodayScheduleCalendarParentData(
    val durationInMinutes: Int,
    val startTimeOffset: Int,
) : ParentDataModifier {

    override fun Density.modifyParentData(parentData: Any?) =
        this@TodayScheduleCalendarParentData

}