package com.muammarahlnn.learnyscape.core.data.repository

import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SchedulesRepository, 08/12/2023 22.20
 */
interface SchedulesRepository {

    fun getSchedules(): Flow<List<ScheduleModel>>
}