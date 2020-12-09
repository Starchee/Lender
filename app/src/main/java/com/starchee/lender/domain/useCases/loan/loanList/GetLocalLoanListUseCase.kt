package com.starchee.lender.domain.useCases.loan.loanList

import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.repositories.LocalLoanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLocalLoanListUseCase @Inject constructor(private val localLoanRepository: LocalLoanRepository) {

    operator fun invoke(): Single<List<Loan>> =
        localLoanRepository.getLoanList()

}