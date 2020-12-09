package com.starchee.lender.di.modules.loan.loanList

import com.starchee.lender.di.scopes.LoanListScope
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.useCases.loan.loanList.GetRemoteLoanListUseCase
import dagger.Module
import dagger.Provides

@Module
class GeRemoteLoanListUseCaseModule {

    @LoanListScope
    @Provides
    fun provideGetRemoteLoanListUseCase(
        remoteLoanRepository: RemoteLoanRepository,
        localLoanRepository: LocalLoanRepository,
        localRepository: LocalRepository
    ) = GetRemoteLoanListUseCase(remoteLoanRepository, localLoanRepository, localRepository)
}