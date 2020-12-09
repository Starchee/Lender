package com.starchee.lender.di.modules.app;

import com.starchee.lender.data.dataSource.loan.LoanDao
import com.starchee.lender.data.dataSource.loan.LocalLoanDataSource
import com.starchee.lender.data.dataSource.loan.LocalLoanDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalLoanDataSourceImplModule {

    @Singleton
    @Provides
    fun provideLocalLoanDataSourceImpl(loanDao: LoanDao): LocalLoanDataSource =
        LocalLoanDataSourceImpl(loanDao)
}
