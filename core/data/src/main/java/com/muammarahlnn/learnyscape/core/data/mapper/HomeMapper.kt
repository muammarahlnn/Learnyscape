package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.ClassInfoModel
import com.muammarahlnn.learnyscape.core.network.model.response.ClassInfoResponse


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeMapper, 12/10/2023 22.31 by Muammar Ahlan Abimanyu
 */

fun List<ClassInfoResponse>.toClassesInfoModel() = map {
    it.toClassInfoModel()
}

fun ClassInfoResponse.toClassInfoModel() = ClassInfoModel(
    id = id,
    className = className,
    lecturerNames = lecturerNames,
)