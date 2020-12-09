package com.starchee.lender.domain.useCases.loan.loanApplication

import com.starchee.lender.domain.entities.LoanApplication
import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import io.reactivex.Completable
import javax.inject.Inject

class CreateLoanAppUseCase @Inject constructor(
    private val remoteLoanRepository: RemoteLoanRepository,
    private val localRepository: LocalRepository
) {

    operator fun invoke(
        loanApplication: LoanApplication,
        loanCondition: LoanCondition
    ): Completable =
        remoteLoanRepository.makeLoanApplication(
            localRepository.getToken(),
            loanApplication,
            loanCondition
        ).toCompletable()

}