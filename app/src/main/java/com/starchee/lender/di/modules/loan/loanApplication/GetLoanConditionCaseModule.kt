package com.starchee.lender.di.modules.loan.loanApplication

import com.starchee.lender.di.scopes.LoanAppScope
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.useCases.loan.loanApplication.GetLoanConditionUseCase
import dagger.Module
import dagger.Provides

@Module
class GetLoanConditionCaseModule {

    @LoanAppScope
    @Provides
    fun provideGetLoanConditionUseCase(
        remoteLoanRepository: RemoteLoanRepository,
        localRepository: LocalRepository
    ) = GetLoanConditionUseCase(remoteLoanRepository, localRepository)
}