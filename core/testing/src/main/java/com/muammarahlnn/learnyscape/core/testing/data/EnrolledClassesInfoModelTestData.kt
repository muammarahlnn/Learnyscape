package com.muammarahlnn.learnyscape.core.testing.data

import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel

/**
 * @Author Muammar Ahlan Abimanyu
 * @File EnrolledClassesInfoModelTestData, 27/05/2024 18.53
 */

val enrolledClassesInfoModelTestData: List<EnrolledClassInfoModel> = listOf(
    EnrolledClassInfoModel(
        id = "1",
        className = "Android Development",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
    EnrolledClassInfoModel(
        id = "2",
        className = "Machine Learning",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
    EnrolledClassInfoModel(
        id = "3",
        className = "Pengantar Pemrograman",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
)