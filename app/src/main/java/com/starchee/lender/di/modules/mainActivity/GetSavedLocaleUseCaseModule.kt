package com.starchee.lender.di.modules.mainActivity

import com.starchee.lender.di.scopes.MainActivityScope
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.useCases.GetSavedLocaleUseCase
import dagger.Module
import dagger.Provides

@Module
class GetSavedLocaleUseCaseModule {

    @MainActivityScope
    @Provides
    fun provideGetSavedLocaleUseCase(localRepository: LocalRepository) =
        GetSavedLocaleUseCase(localRepository)
}