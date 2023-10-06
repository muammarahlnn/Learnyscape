package com.muammarahlnn.learnyscape.core.domain

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.domain.base.BaseSuspendUseCase
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SaveAccessTokenUseCase, 07/10/2023 03.06 by Muammar Ahlan Abimanyu
 */
class SaveAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseSuspendUseCase<SaveAccessTokenUseCase.Params> {

    override suspend fun execute(params: Params) {
        loginRepository.saveAccessToken(params.accessToken)
    }

    class Params(
        internal val accessToken: String,
    )
}