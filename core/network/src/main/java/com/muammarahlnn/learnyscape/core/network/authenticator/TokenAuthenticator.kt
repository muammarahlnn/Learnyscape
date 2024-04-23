package com.muammarahlnn.learnyscape.core.network.authenticator

import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.di.NO_AUTH
import com.muammarahlnn.learnyscape.core.network.model.request.RefreshTokenRequest
import com.muammarahlnn.learnyscape.core.network.model.response.LoginResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Named

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TokenAuthenticator, 23/04/2024 17.15
 */
class TokenAuthenticator @Inject constructor(
    private val learnyscapePreferences: LearnyscapePreferencesDataSource,
    @Named(NO_AUTH) private val usersApi: UsersApi,
) : Authenticator {

    private val isTokenRefreshing = AtomicBoolean(false)

    private var request: Request? = null

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        request = null

        if (!isTokenRefreshing.get()) {
            isTokenRefreshing.set(true)

            handleRefreshToken()
            request = buildRequest(response.request.newBuilder())

            isTokenRefreshing.set(false)
        } else {
            waitForRefresh(response)
        }

        request
    }

    private suspend fun handleRefreshToken() {
        val refreshedTokenResponse = fetchNewRefreshToken()
        saveNewTokens(
            accessToken = refreshedTokenResponse.accessToken,
            refreshToken = refreshedTokenResponse.refreshToken,
        )
    }

    // The refresh token response is same with the LoginResponse
    private suspend fun fetchNewRefreshToken(): LoginResponse = usersApi.postRefreshToken(
        refreshTokenRequest = RefreshTokenRequest(
            refreshToken = learnyscapePreferences.getRefreshToken().first()
        )
    ).data

    private suspend fun saveNewTokens(
        accessToken: String,
        refreshToken: String,
    ) {
        learnyscapePreferences.saveAccessToken(accessToken)
        learnyscapePreferences.saveRefreshToken(refreshToken)
    }

    private suspend fun waitForRefresh(response: Response) {
        while (isTokenRefreshing.get()) {
            delay(100)
        }
        request = buildRequest(response.request.newBuilder())
    }

    private fun buildRequest(requestBuilder: Request.Builder): Request {
        val token = runBlocking {
            learnyscapePreferences.getAccessToken().first()
        }

        return requestBuilder
            .header("Authorization", "Bearer $token")
            .build()
    }
}