package com.muammarahlnn.learnyscape.core.domain.home

import com.muammarahlnn.learnyscape.core.data.repository.HomeRepository
import com.muammarahlnn.learnyscape.core.domain.base.BaseUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file GetLoggedInUserUseCase, 09/10/2023 22.37 by Muammar Ahlan Abimanyu
 */
class GetLoggedInUserUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) : BaseUseCase<NoParams, UserModel> {

    override fun execute(params: NoParams): Flow<UserModel> =
        homeRepository.getLoggedInUser()
}