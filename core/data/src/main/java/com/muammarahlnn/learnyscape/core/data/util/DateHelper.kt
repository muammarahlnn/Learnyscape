package com.muammarahlnn.learnyscape.core.data.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DateHelper, 17/01/2024 19.25
 */

private const val ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
private const val DISPLAY_FORMATTER = "d MMM yyyy, HH:mm"

fun formatIsoDate(isoDate: String): String {
    val isoFormatter = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT)
    val displayFormatter = DateTimeFormatter.ofPattern(DISPLAY_FORMATTER)

    val localDateTime = LocalDateTime.parse(isoDate, isoFormatter)
    return localDateTime.format(displayFormatter)
}