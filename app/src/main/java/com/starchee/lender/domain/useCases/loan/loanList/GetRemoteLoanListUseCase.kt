package com.starchee.lender.domain.useCases.loan.loanList

import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRemoteLoanListUseCase @Inject constructor(
    private val remoteLoanRepository: RemoteLoanRepository,
    private val localLoanRepository: LocalLoanRepository,
    private val localRepository: LocalRepository
) {

    operator fun invoke(): Single<List<Loan>> =
        remoteLoanRepository.getLoanList(localRepository.getToken())
            .doOnSuccess { localLoanRepository.saveLoanList(it) }

}