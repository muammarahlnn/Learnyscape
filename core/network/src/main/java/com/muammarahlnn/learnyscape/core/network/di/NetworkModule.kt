package com.muammarahlnn.learnyscape.core.network.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.network.BuildConfig
import com.muammarahlnn.learnyscape.core.network.api.AnnouncementsApi
import com.muammarahlnn.learnyscape.core.network.api.AttachmentApi
import com.muammarahlnn.learnyscape.core.network.api.ClassesApi
import com.muammarahlnn.learnyscape.core.network.api.NotificationsApi
import com.muammarahlnn.learnyscape.core.network.api.QuizzesApi
import com.muammarahlnn.learnyscape.core.network.api.ReferencesApi
import com.muammarahlnn.learnyscape.core.network.api.TasksApi
import com.muammarahlnn.learnyscape.core.network.api.UsersApi
import com.muammarahlnn.learnyscape.core.network.api.WaitingListApi
import com.muammarahlnn.learnyscape.core.network.authenticator.TokenAuthenticator
import com.muammarahlnn.learnyscape.core.network.interceptor.BearerTokenInterceptor
import com.muammarahlnn.learnyscape.core.network.interceptor.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
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
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    @Named(BEARER_TOKEN_AUTH)
    fun providesBearerTokenOkHttpClient(
        @ApplicationContext context: Context,
        learnyscapePreferences: LearnyscapePreferencesDataSource,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient = buildOkHttpClient(
        context = context,
        customInterceptor = BearerTokenInterceptor(learnyscapePreferences),
        customAuthenticator = tokenAuthenticator,
    )

    @Provides
    @Singleton
    @Named(NO_AUTH)
    fun providesDefaultOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient = buildOkHttpClient(context)

    @Provides
    @Singleton
    @Named(NO_AUTH)
    fun providesNoAuthUsersApi(
        networkJson: Json,
        @Named(NO_AUTH) client: OkHttpClient,
    ): UsersApi = buildRetrofit(networkJson, client).create(UsersApi::class.java)

    @Provides
    @Singleton
    @Named(BEARER_TOKEN_AUTH)
    fun providesBearerTokenUsersApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): UsersApi = buildRetrofit(networkJson, client).create(UsersApi::class.java)

    @Provides
    @Singleton
    fun providesClassesApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): ClassesApi = buildRetrofit(networkJson, client).create(ClassesApi::class.java)

    @Provides
    @Singleton
    fun providesAnnouncementsApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): AnnouncementsApi = buildRetrofit(networkJson, client).create(AnnouncementsApi::class.java)

    @Provides
    @Singleton
    fun providesReferencesApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): ReferencesApi = buildRetrofit(networkJson, client).create(ReferencesApi::class.java)

    @Provides
    @Singleton
    fun providesTasksApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): TasksApi = buildRetrofit(networkJson, client).create(TasksApi::class.java)

    @Provides
    @Singleton
    fun providesQuizzesApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): QuizzesApi = buildRetrofit(networkJson, client).create(QuizzesApi::class.java)

    @Provides
    @Singleton
    fun providesWaitingListApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): WaitingListApi = buildRetrofit(networkJson, client).create(WaitingListApi::class.java)

    @Provides
    @Singleton
    fun providesAttachmentApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): AttachmentApi = buildRetrofit(networkJson, client).create(AttachmentApi::class.java)

    @Provides
    @Singleton
    fun providesNotificationsApi(
        networkJson: Json,
        @Named(BEARER_TOKEN_AUTH) client: OkHttpClient,
    ): NotificationsApi = buildRetrofit(networkJson, client).create(NotificationsApi::class.java)
}

internal const val BASE_URL = BuildConfig.BASE_URL
internal const val NO_AUTH = "NoAuth"
internal const val BEARER_TOKEN_AUTH = "BearerTokenAuth"

private fun buildOkHttpClient(
    context: Context,
    customInterceptor: Interceptor? = null,
    customAuthenticator: Authenticator? = null,
): OkHttpClient = OkHttpClient.Builder().apply {
    if (customInterceptor != null) addInterceptor(customInterceptor)
    addInterceptor(NetworkConnectionInterceptor(context))
    addInterceptor(
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        }
    )

    if (customAuthenticator != null) authenticator(customAuthenticator)

    connectTimeout(10, TimeUnit.SECONDS)
    writeTimeout(10, TimeUnit.SECONDS)
    readTimeout(30, TimeUnit.SECONDS)
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



