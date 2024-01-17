package com.muammarahlnn.learnyscape.core.domain.joinrequest

import com.muammarahlnn.learnyscape.core.model.data.WaitingListModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetWaitingClassUseCase, 17/01/2024 21.57
 */
fun interface GetWaitingClassUseCase {

    operator fun invoke(classId: String): Flow<List<WaitingListModel>>
}