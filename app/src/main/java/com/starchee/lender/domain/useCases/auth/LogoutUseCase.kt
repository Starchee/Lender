package com.starchee.lender.domain.useCases.auth

import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Completable
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val localLoanRepository: LocalLoanRepository,
    private val localRepository: LocalRepository
) {
    operator fun invoke(): Completable =
        localLoanRepository.clearCache().doOnComplete { localRepository.clearToken() }

}