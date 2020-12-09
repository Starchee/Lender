package com.starchee.lender.di.modules.local

import com.starchee.lender.data.dataSource.local.LocalDataSource
import com.starchee.lender.data.repositories.LocalRepositoryImpl
import com.starchee.lender.domain.repositories.LocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalRepositoryModule {

    @Singleton
    @Provides
    fun provideLocalRepository(localDataSource: LocalDataSource): LocalRepository =
        LocalRepositoryImpl(localDataSource)
}