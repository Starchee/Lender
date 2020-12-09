package com.starchee.lender.di.modules.auth

import com.starchee.lender.data.dataSource.auth.AuthDataSourceImpl
import com.starchee.lender.data.repositories.AuthRepositoryImpl
import com.starchee.lender.di.scopes.AuthScope
import com.starchee.lender.domain.repositories.AuthRepository
import dagger.Module
import dagger.Provides

@Module
class AuthRepositoryModule {

    @AuthScope
    @Provides
    fun provideAuthRepository(authDataSourceImpl: AuthDataSourceImpl): AuthRepository =
        AuthRepositoryImpl(authDataSourceImpl)
}