package com.starchee.lender.di.modules.auth

import com.starchee.lender.data.dataSource.auth.AuthApi
import com.starchee.lender.di.scopes.AuthScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthApiModule {

    @AuthScope
    @Provides
    fun provideAuthApi(retrofit: Retrofit) = retrofit.create(AuthApi::class.java)
}