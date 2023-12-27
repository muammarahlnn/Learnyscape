package com.muammarahlnn.learnyscape.core.ui.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TimeHelper, 27/12/2023 08.04
 */
fun getLocalDateTimeNow(): LocalDateTime = LocalDateTime.now()

fun getCurrentDate(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    return getLocalDateTimeNow().format(dateFormatter)
}

fun getCurrentDay(): String {
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE")
    return getLocalDateTimeNow().format(dayOfWeekFormatter)
}

fun getCurrentTime(): String {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return getLocalDateTimeNow().format(timeFormatter)
}