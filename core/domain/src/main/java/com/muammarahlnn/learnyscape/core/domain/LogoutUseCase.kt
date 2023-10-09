package com.muammarahlnn.learnyscape.core.domain

import com.muammarahlnn.learnyscape.core.data.repository.ProfileRepository
import com.muammarahlnn.learnyscape.core.domain.base.BaseSuspendUseCase
import com.muammarahlnn.learnyscape.core.model.data.NoParams
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LogoutUseCase, 09/10/2023 21.36 by Muammar Ahlan Abimanyu
 */
class LogoutUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseSuspendUseCase<NoParams> {
    override suspend fun execute(params: NoParams) {
        profileRepository.logout()
    }
}