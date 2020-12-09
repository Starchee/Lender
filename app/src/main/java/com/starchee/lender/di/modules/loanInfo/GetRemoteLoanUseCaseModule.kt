package com.starchee.lender.di.modules.loanInfo

import com.starchee.lender.di.scopes.LoanInfoScope
import com.starchee.lender.domain.useCases.loanInfo.GetRemoteLoanUseCase
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import dagger.Module
import dagger.Provides

@Module
class GetRemoteLoanUseCaseModule {

    @LoanInfoScope
    @Provides
    fun provideGetRemoteLoanUseCaseModule(remoteLoanRepository: RemoteLoanRepository, localRepository: LocalRepository) =
        GetRemoteLoanUseCase(remoteLoanRepository, localRepository)
}