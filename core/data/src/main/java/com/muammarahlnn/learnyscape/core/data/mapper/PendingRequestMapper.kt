package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel
import com.muammarahlnn.learnyscape.core.network.model.response.PendingRequestResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File PendingRequestMapper, 29/02/2024 22.13
 */
fun List<PendingRequestResponse>.toPendingRequestModels(): List<PendingRequestModel> = map {
    it.toPendingRequestModel()
}

fun PendingRequestResponse.toPendingRequestModel() = PendingRequestModel(
    id = id,
    classId = classId,
    className = className,
    lecturerNames = lecturers,
)