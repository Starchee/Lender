package com.starchee.lender.di.modules.auth

import com.starchee.lender.di.scopes.AuthScope
import com.starchee.lender.domain.useCases.auth.RegistrationUseCase
import com.starchee.lender.domain.repositories.AuthRepository
import com.starchee.lender.domain.repositories.LocalRepository
import dagger.Module
import dagger.Provides

@Module
class RegistrationUseCaseModule {

    @AuthScope
    @Provides
    fun provideRegistrationUseCase(
        authRepository: AuthRepository,
        localRepository: LocalRepository
    ) =
        RegistrationUseCase(authRepository, localRepository)
}