package com.muammarahlnn.learnyscape.core.model.data


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file DayModel, 19/11/2023 05.44 by Muammar Ahlan Abimanyu
 */
enum class DayModel(val displayedText: String) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    companion object {

        private val dayLookup by lazy {
            entries.associateBy { it.name }
        }

        fun fromDayName(dayName: String): DayModel = dayLookup[dayName]
            ?: throw IllegalArgumentException("The given day not matched any DayModel")

    }
}