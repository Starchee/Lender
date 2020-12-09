package com.starchee.lender.di.modules.loan.loanApplication

import com.starchee.lender.di.scopes.LoanAppScope
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.useCases.loan.loanApplication.CreateLoanAppUseCase
import dagger.Module
import dagger.Provides

@Module
class CreateLoanAppUseCaseModule {

    @LoanAppScope
    @Provides
    fun provideCreateLoanAppUseCase(
        remoteLoanRepository: RemoteLoanRepository,
        localRepository: LocalRepository
    ) =
        CreateLoanAppUseCase(remoteLoanRepository, localRepository)
}