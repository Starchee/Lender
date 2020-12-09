package com.starchee.lender.di.modules.app

import com.starchee.lender.data.dataSource.loan.LoanApi
import com.starchee.lender.data.dataSource.loan.RemoteLoanDataSource
import com.starchee.lender.data.dataSource.loan.RemoteLoanDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteLoanDataSourceModuleImpl {

    @Singleton
    @Provides
    fun provideRemoteLoanDataSourceImpl(loanApi: LoanApi): RemoteLoanDataSource =
        RemoteLoanDataSourceImpl(loanApi)
}
