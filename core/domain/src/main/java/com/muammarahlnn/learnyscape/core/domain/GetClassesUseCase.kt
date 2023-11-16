package com.muammarahlnn.learnyscape.core.domain

import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.domain.base.BaseUseCase
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetClassesUseCase, 12/10/2023 22.34 by Muammar Ahlan Abimanyu
 */
class GetClassesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseUseCase<NoParams, List<EnrolledClassInfoModel>> {

    override fun execute(params: NoParams): Flow<List<EnrolledClassInfoModel>> =
        homeRepository.getClasses()
}