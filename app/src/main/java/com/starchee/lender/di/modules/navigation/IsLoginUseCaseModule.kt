package com.starchee.lender.di.modules.navigation

import com.starchee.lender.di.scopes.NavigationScope
import com.starchee.lender.domain.useCases.auth.IsLoginUseCase
import com.starchee.lender.domain.repositories.LocalRepository
import dagger.Module
import dagger.Provides

@Module
class IsLoginUseCaseModule {

    @NavigationScope
    @Provides
    fun provideIsLoginUseCase(localRepository: LocalRepository) = IsLoginUseCase(localRepository)
}