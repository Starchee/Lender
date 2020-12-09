package com.starchee.lender.domain.useCases.loanInfo

import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRemoteLoanUseCase @Inject constructor(
    private val remoteLoanRepository: RemoteLoanRepository,
    private val localRepository: LocalRepository
) {

    operator fun invoke(id: Int): Single<Loan> =
        remoteLoanRepository.getLoanById(localRepository.getToken(), id)

}