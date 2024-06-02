package com.muammarahlnn.learnyscape.core.testing.data

import com.muammarahlnn.learnyscape.core.network.model.response.EnrolledClassInfoResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EnrolledClassesInfoResponseTestData, 31/05/2024 19.15
 */
val enrolledClassesInfoResponseTestData: List<EnrolledClassInfoResponse> = listOf(
    EnrolledClassInfoResponse(
        id = "1",
        className = "Android Development",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
    EnrolledClassInfoResponse(
        id = "2",
        className = "Machine Learning",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
    EnrolledClassInfoResponse(
        id = "3",
        className = "Pengantar Pemrograman",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
)