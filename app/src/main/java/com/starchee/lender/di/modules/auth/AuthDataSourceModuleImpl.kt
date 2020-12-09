package com.starchee.lender.di.modules.auth

import com.starchee.lender.data.dataSource.auth.AuthApi
import com.starchee.lender.data.dataSource.auth.AuthDataSourceImpl
import com.starchee.lender.di.scopes.AuthScope
import dagger.Module
import dagger.Provides

@Module
class AuthDataSourceModuleImpl {

    @AuthScope
    @Provides
    fun provideAuthDataSourceImpl(authApi: AuthApi) = AuthDataSourceImpl(authApi)
}