package com.muammarahlnn.learnyscape.core.model.data


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file UserModel, 07/10/2023 21.47 by Muammar Ahlan Abimanyu
 */
data class UserModel(
    val id: String,
    val username: String,
    val fullName: String,
    val email: String,
    val role: UserRole,
)

enum class UserRole {
    STUDENT, LECTURER;

    companion object {

        fun getRole(role: String): UserRole =
            when (role) {
                STUDENT.name -> STUDENT
                LECTURER.name -> LECTURER
                else -> throw IllegalStateException("Role's not found")
            }
    }
}