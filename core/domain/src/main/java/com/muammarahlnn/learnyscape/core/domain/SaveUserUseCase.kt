package com.muammarahlnn.learnyscape.core.domain

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.domain.base.BaseUseCase
import com.muammarahlnn.learnyscape.core.model.data.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SaveUserUseCase, 09/10/2023 15.47 by Muammar Ahlan Abimanyu
 */
class SaveUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) : BaseUseCase<SaveUserUseCase.Params, UserModel> {

    override fun execute(params: Params): Flow<UserModel> =
        loginRepository.saveUser(params.token)

    class Params(
        internal val token: String,
    )
}