package com.muammarahlnn.learnyscape.core.domain.di

import com.muammarahlnn.learnyscape.core.data.repository.ClassMembersRepository
import com.muammarahlnn.learnyscape.core.domain.classmembers.GetClassMembersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ClassMembersInteractorModule, 25/01/2024 22.17
 */
@Module
@InstallIn(ViewModelComponent::class)
object ClassMembersInteractorModule {

    @Provides
    @ViewModelScoped
    fun providesGetClassMembersUseCase(
        classMembersRepository: ClassMembersRepository
    ): GetClassMembersUseCase = GetClassMembersUseCase(
        classMembersRepository::getClassMembers
    )
}