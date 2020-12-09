package com.starchee.lender.di.modules.auth

import com.starchee.lender.di.scopes.AuthScope
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.useCases.auth.LogoutUseCase
import dagger.Module
import dagger.Provides

@Module
class LogoutUseCaseModule {

    @AuthScope
    @Provides
    fun provideLogoutUseCase(
        localLoanRepository: LocalLoanRepository,
        localRepository: LocalRepository
    ) = LogoutUseCase(localLoanRepository, localRepository)
}