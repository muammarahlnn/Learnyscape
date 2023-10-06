package com.muammarahlnn.learnyscape.core.datastore.di

import android.content.Context
import com.muammarahlnn.learnyscape.core.datastore.LearnyscapePreferencesDataSource
import com.muammarahlnn.learnyscape.core.datastore.learnyscapePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file DataStoreModule, 07/10/2023 02.43 by Muammar Ahlan Abimanyu
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesLearnyscapePreferencesDataStore(
        @ApplicationContext context: Context
    ): LearnyscapePreferencesDataSource = LearnyscapePreferencesDataSource(
        context.learnyscapePreferences
    )
}