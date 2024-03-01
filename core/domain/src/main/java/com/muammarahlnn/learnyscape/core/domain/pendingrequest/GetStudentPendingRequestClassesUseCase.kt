package com.muammarahlnn.learnyscape.core.domain.pendingrequest

import com.muammarahlnn.learnyscape.core.model.data.PendingRequestModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetStudentPendingRequestClassesUseCase, 29/02/2024 22.15
 */
fun interface GetStudentPendingRequestClassesUseCase : () -> Flow<List<PendingRequestModel>>