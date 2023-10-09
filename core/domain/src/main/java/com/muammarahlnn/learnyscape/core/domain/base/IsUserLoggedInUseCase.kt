package com.muammarahlnn.learnyscape.core.domain.base

import com.muammarahlnn.learnyscape.core.data.repository.UserRepository
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file IsUserLoggedInUseCase, 08/10/2023 20.33 by Muammar Ahlan Abimanyu
 */
class IsUserLoggedInUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<NoParams, Boolean> {

    override fun execute(params: NoParams): Flow<Boolean> =
        userRepository.isUserLoggedIn()
}