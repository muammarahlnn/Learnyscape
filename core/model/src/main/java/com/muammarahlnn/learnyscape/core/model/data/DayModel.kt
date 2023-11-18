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

        fun getDayModel(day: String): DayModel = when (day) {
            MONDAY.name -> MONDAY
            TUESDAY.name -> TUESDAY
            WEDNESDAY.name -> WEDNESDAY
            THURSDAY.name -> THURSDAY
            FRIDAY.name -> FRIDAY
            SATURDAY.name -> SATURDAY
            SUNDAY.name -> SUNDAY
            else -> throw IllegalArgumentException("The given day not matched any DayModel")
        }
    }
}