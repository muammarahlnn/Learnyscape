package com.muammarahlnn.learnyscape.core.domain.login

import com.muammarahlnn.learnyscape.core.testing.repository.TestLoginRepository
import com.muammarahlnn.learnyscape.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @Author Muammar Ahlan Abimanyu
 * @File IsUserLoggedInUseCaseTest, 27/05/2024 00.32
 */
class IsUserLoggedInUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val loginRepository = TestLoginRepository()

    private val useCase = IsUserLoggedInUseCase(loginRepository)

    private lateinit var isUserLoggedInFlow: Flow<Boolean>

    @Before
    fun setup() {
        isUserLoggedInFlow = flow { emit(useCase()) }
    }


    @Test
    fun `when user login, user logged in is returned`() = runTest {
        // when
        loginRepository.userLogin()

        // then
        assertEquals(
            isUserLoggedInFlow.first(),
            true
        )
    }

    @Test
    fun `when user logout, user not logged in is returned`() = runTest {
        // when
        loginRepository.userLogout()

        // then
        assertEquals(
            isUserLoggedInFlow.first(),
            false
        )
    }
}