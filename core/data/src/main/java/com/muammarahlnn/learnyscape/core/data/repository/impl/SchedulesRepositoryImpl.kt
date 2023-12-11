package com.muammarahlnn.learnyscape.core.data.repository.impl

import com.muammarahlnn.learnyscape.core.data.mapper.toScheduleModels
import com.muammarahlnn.learnyscape.core.data.repository.SchedulesRepository
import com.muammarahlnn.learnyscape.core.model.data.ScheduleModel
import com.muammarahlnn.learnyscape.core.network.datasource.SchedulesNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SchedulesRepository, 08/12/2023 22.27
 */
class SchedulesRepositoryImpl @Inject constructor(
    private val schedulesNetworkDataSource: SchedulesNetworkDataSource
) : SchedulesRepository {

    override fun getSchedules(): Flow<List<ScheduleModel>> =
        schedulesNetworkDataSource.getSchedules().map { scheduleResponses ->
            scheduleResponses.toScheduleModels()
        }
}