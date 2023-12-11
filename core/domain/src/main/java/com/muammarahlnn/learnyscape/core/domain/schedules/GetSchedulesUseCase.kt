package com.muammarahlnn.learnyscape.core.domain.schedules

import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File GetSchdulesUseCase, 08/12/2023 22.56
 */
fun interface GetSchedulesUseCase : () -> Flow<List<ScheduleModel>>