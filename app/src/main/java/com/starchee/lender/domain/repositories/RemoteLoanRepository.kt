package com.starchee.lender.domain.repositories

import com.starchee.lender.domain.entities.LoanApplication
import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.entities.Loan
import io.reactivex.Single

interface RemoteLoanRepository {

    fun getLoanCondition(token: String): Single<LoanCondition>
    fun getLoanList(token: String): Single<List<Loan>>
    fun makeLoanApplication(
        token: String,
        loanApplication: LoanApplication,
        loanCondition: LoanCondition
    ): Single<Loan>
    fun getLoanById(token: String, id: Int): Single<Loan>
}