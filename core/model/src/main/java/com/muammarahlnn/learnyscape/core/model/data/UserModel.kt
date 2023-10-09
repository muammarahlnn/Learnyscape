package com.muammarahlnn.learnyscape.core.model.data


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UserModel, 07/10/2023 21.47 by Muammar Ahlan Abimanyu
 */
data class UserModel(
    val id: String = "",
    val username: String = "",
    val fullName: String = "",
    val role: UserRole = UserRole.NOT_LOGGED_IN,
)

enum class UserRole {
    NOT_LOGGED_IN, STUDENT, LECTURER;

    companion object {

        fun getRole(role: String): UserRole =
            when (role) {
                STUDENT.name -> STUDENT
                LECTURER.name -> LECTURER
                else -> NOT_LOGGED_IN
            }
    }
}