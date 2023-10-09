package com.muammarahlnn.learnyscape.core.network.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.network.BuildConfig
import com.muammarahlnn.learnyscape.core.network.api.LoginApi
import com.muammarahlnn.learnyscape.core.network.interceptor.BearerTokenInterceptor
import com.muammarahlnn.learnyscape.core.network.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file NetworkModule, 29/09/2023 21.23 by Muammar Ahlan Abimanyu
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesLoginApi(
        networkJson: Json,
        @Named(DEFAULT_OK_HTTP_CLIENT_QUALIFIER) client: OkHttpClient,
    ): LoginApi = buildRetrofit(networkJson, client).create(LoginApi::class.java)

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    @Named(BEARER_TOKEN_OK_HTTP_CLIENT_QUALIFIER)
    fun providesBearerTokenOkHttpClient(
        @ApplicationContext context: Context,
        learnyscapePreferences: LearnyscapePreferencesDataSource,
    ): OkHttpClient = buildOkHttpClient(
        context = context,
        customInterceptor = BearerTokenInterceptor(learnyscapePreferences)
    )

    @Provides
    @Singleton
    @Named(DEFAULT_OK_HTTP_CLIENT_QUALIFIER)
    fun providesDefaultOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient = buildOkHttpClient(context)
}

private const val BASE_URL = BuildConfig.BASE_URL
private const val DEFAULT_OK_HTTP_CLIENT_QUALIFIER = "Default"
private const val BEARER_TOKEN_OK_HTTP_CLIENT_QUALIFIER = "BearerToken"

private fun buildOkHttpClient(
    context: Context,
    customInterceptor: Interceptor? = null,
): OkHttpClient = OkHttpClient.Builder().apply {
    if (customInterceptor != null) {
        addInterceptor(customInterceptor)
    }
    addInterceptor(NetworkConnectionInterceptor(context))
    addInterceptor(
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }
    )
    connectTimeout(120, TimeUnit.SECONDS)
    readTimeout(120, TimeUnit.SECONDS)
}.build()

private fun buildRetrofit(
    networkJson: Json,
    client: OkHttpClient,
): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        networkJson.asConverterFactory(
            "application/json".toMediaType()
        )
    )
    .client(client)
    .build()



