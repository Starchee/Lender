package com.starchee.lender.di.modules.app

import com.starchee.lender.data.dataSource.loan.LoanApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class LoanApiModule {

    @Singleton
    @Provides
    fun provideLoanApi(retrofit: Retrofit) = retrofit.create(LoanApi::class.java)

}