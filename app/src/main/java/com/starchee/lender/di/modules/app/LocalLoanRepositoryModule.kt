package com.starchee.lender.di.modules.app

import com.starchee.lender.data.dataSource.loan.LocalLoanDataSource
import com.starchee.lender.data.repositories.LocalLoanRepositoryImpl
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.data.utils.DateUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalLoanRepositoryModule {

    @Singleton
    @Provides
    fun provideLocalLoanRepositoryModule(
        localLoanDataSource: LocalLoanDataSource,
        dateUtils: DateUtils
    ): LocalLoanRepository =
        LocalLoanRepositoryImpl(localLoanDataSource, dateUtils)
}