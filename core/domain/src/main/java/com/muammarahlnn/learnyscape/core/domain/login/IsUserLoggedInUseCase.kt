package com.muammarahlnn.learnyscape.core.domain.login

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file IsUserLoggedInUseCase, 08/10/2023 20.33 by Muammar Ahlan Abimanyu
 */
class IsUserLoggedInUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {

    operator fun invoke(): Boolean = runBlocking {
        loginRepository.isUserLoggedIn().first()
    }
}