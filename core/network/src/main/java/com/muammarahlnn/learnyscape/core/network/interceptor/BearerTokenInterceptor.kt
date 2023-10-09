package com.muammarahlnn.learnyscape.core.network.interceptor

import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file BearerTokenInterceptor, 07/10/2023 21.10 by Muammar Ahlan Abimanyu
 */
class BearerTokenInterceptor(
    private val learnyscapePreference: LearnyscapePreferencesDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            learnyscapePreference.getAccessToken().first()
        }
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}