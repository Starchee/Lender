package com.starchee.lender.domain.repositories

import com.starchee.lender.domain.entities.Loan
import io.reactivex.Completable
import io.reactivex.Single

interface LocalLoanRepository {
    fun getLoanList(): Single<List<Loan>>
    fun getLoanById(id: Int): Single<Loan>
    fun saveLoanList(loanList: List<Loan>)
    fun clearCache(): Completable
}