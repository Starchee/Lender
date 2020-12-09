package com.starchee.lender.di.modules.loanInfo

import com.starchee.lender.di.scopes.LoanInfoScope
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.useCases.loanInfo.GetLocalLoanUseCase
import dagger.Module
import dagger.Provides

@Module
class GetLocalLoanUseCaseModule {

    @LoanInfoScope
    @Provides
    fun provideGetLocalLoanUseCaseModule(localLoanRepository: LocalLoanRepository) =
        GetLocalLoanUseCase(localLoanRepository)
}