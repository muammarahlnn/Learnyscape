package com.muammarahlnn.learnyscape.core.data.mapper

import com.muammarahlnn.learnyscape.core.model.data.WaitingListModel
import com.muammarahlnn.learnyscape.core.network.model.response.WaitingListResponse

/**
 * @Author Muammar Ahlan Abimanyu
 * @File JoinRequestMapper, 17/01/2024 21.54
 */
fun List<WaitingListResponse>.toWaitingListModels() = map {
    it.toWaitingListModel()
}

fun WaitingListResponse.toWaitingListModel() = WaitingListModel(
    id = id,
    userId = userId,
    studentId = studentId,
    fullName = fullName,
)