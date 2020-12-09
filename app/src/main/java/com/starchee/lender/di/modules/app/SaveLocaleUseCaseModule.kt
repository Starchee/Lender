package com.starchee.lender.di.modules.app

import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.useCases.SaveLocaleUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SaveLocaleUseCaseModule {

    @Singleton
    @Provides
    fun provideSaveLocaleUseCase(localRepository: LocalRepository) =
        SaveLocaleUseCase(localRepository)
}