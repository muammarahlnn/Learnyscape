package com.muammarahlnn.learnyscape.core.domain

import com.muammarahlnn.learnyscape.core.data.repository.LoginRepository
import com.muammarahlnn.learnyscape.core.model.data.LoginModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file PostLoginUserUseCase, 02/10/2023 16.31 by Muammar Ahlan Abimanyu
 */
class PostLoginUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseUseCase<PostLoginUserUseCase.Params, LoginModel> {

    override fun execute(params: Params): Flow<LoginModel> =
        loginRepository.postLoginUser(
            username = params.username,
            password = params.password
        )

    class Params(
        internal val username: String,
        internal val password: String,
    )
}