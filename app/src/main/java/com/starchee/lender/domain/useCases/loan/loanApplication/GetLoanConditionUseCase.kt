package com.starchee.lender.domain.useCases.loan.loanApplication

import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLoanConditionUseCase @Inject constructor(
    private val remoteLoanRepository: RemoteLoanRepository,
    private val localRepository: LocalRepository
) {

    operator fun invoke(): Single<LoanCondition> =
        remoteLoanRepository.getLoanCondition(localRepository.getToken())

}