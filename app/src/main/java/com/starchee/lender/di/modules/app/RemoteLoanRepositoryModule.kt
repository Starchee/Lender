package com.starchee.lender.di.modules.app

import com.starchee.lender.data.dataSource.loan.RemoteLoanDataSource
import com.starchee.lender.data.repositories.RemoteLoanRepositoryImpl
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.data.utils.DateUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteLoanRepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteLoanRepositoryModule(
        remoteLoanDataSource: RemoteLoanDataSource,
        dateUtils: DateUtils
    ): RemoteLoanRepository =
        RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
}