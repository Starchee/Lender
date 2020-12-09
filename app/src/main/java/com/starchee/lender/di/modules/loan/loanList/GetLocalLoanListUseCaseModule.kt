package com.starchee.lender.di.modules.loan.loanList

import com.starchee.lender.di.scopes.LoanListScope
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.useCases.loan.loanList.GetLocalLoanListUseCase
import dagger.Module
import dagger.Provides

@Module
class GetLocalLoanListUseCaseModule {

    @LoanListScope
    @Provides
    fun provideGetLocalLoanListUseCase(localLoanRepository: LocalLoanRepository) =
        GetLocalLoanListUseCase(localLoanRepository)
}