package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.network.model.response.EnrolledClassInfoResponse


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeMapper, 12/10/2023 22.31 by Muammar Ahlan Abimanyu
 */

fun List<EnrolledClassInfoResponse>.toEnrolledClassInfoModels() = map {
    it.toEnrolledClassInfoModel()
}

fun EnrolledClassInfoResponse.toEnrolledClassInfoModel() = EnrolledClassInfoModel(
    id = id,
    className = className,
    lecturerNames = lecturerNames,
)