package com.starchee.lender.domain.useCases.loanInfo

import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.repositories.LocalLoanRepository
import io.reactivex.Single
import javax.inject.Inject

class GetLocalLoanUseCase @Inject constructor(private val localLoanRepository: LocalLoanRepository ) {

    operator fun invoke(id: Int): Single<Loan> =
        localLoanRepository.getLoanById(id)

}