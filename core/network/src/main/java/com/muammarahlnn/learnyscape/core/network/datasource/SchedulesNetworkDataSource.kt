package com.muammarahlnn.learnyscape.core.network.datasource

import com.muammarahlnn.learnyscape.core.network.model.response.ScheduleResponse
import kotlinx.coroutines.flow.Flow

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SchedulesDataSource, 08/12/2023 22.18
 */
interface SchedulesNetworkDataSource {

    fun getSchedules(): Flow<List<ScheduleResponse>>
}