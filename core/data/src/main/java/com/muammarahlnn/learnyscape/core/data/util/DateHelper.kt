package com.muammarahlnn.learnyscape.core.data.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DateHelper, 17/01/2024 19.25
 */

private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DISPLAY_FORMAT = "d MMM yyyy, HH:mm"

fun formatIsoDate(isoDate: String): String {
    val isoFormatter = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT)
    val displayFormatter = DateTimeFormatter.ofPattern(DISPLAY_FORMAT)

    val localDateTime = LocalDateTime.parse(isoDate, isoFormatter)
    return localDateTime.format(displayFormatter)
}

fun formatEpochSeconds(epochSeconds: Long): String {
    val displayFormatter = DateTimeFormatter.ofPattern(DISPLAY_FORMAT)
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(epochSeconds),
        ZoneId.systemDefault()
    )
    return localDateTime.format(displayFormatter)
}