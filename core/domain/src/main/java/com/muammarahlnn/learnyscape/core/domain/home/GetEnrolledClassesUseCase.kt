package com.muammarahlnn.learnyscape.core.domain.home

import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import kotlinx.coroutines.flow.Flow


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetEnrolledClassesUseCase, 16/11/2023 18.14 by Muammar Ahlan Abimanyu
 */
fun interface GetEnrolledClassesUseCase : () -> Flow<List<EnrolledClassInfoModel>>