package com.starchee.lender.data.repositories

import com.starchee.lender.data.dataSource.loan.LocalLoanDataSource
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import com.starchee.lender.data.utils.DateUtils
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.domain.repositories.LocalLoanRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalLoanRepositoryImpl @Inject constructor(
    private val localLoanDataSource: LocalLoanDataSource
) : LocalLoanRepository {

    override fun getLoanList(): Single<List<Loan>> =
        localLoanDataSource.getLoanList().map { loans ->
            loans.map { it.mapToLoanInfo() }
                .sortedByDescending { it.id }
        }

    override fun getLoanById(id: Int): Single<Loan> =
        localLoanDataSource.getLoanById(id).map { loans -> loans.mapToLoanInfo() }


    override fun saveLoanList(loanList: List<Loan>) =
        localLoanDataSource.saveLoanList(loanList.map { loan -> loan.mapToLoanModel() })

    override fun clearCache(): Completable = localLoanDataSource.clearCache()

    private fun LoanModel.mapToLoanInfo() = Loan(
        amount = this.amount,
        date = this.date,
        firstName = this.firstName,
        id = this.id,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phone = this.phone,
        state = LoanState.valueOf(this.state)
    )

    private fun Loan.mapToLoanModel() = LoanModel(
        amount = this.amount,
        date = this.date,
        firstName = this.firstName,
        id = this.id,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phone = this.phone,
        state = this.state.toString()
    )

    private fun LoanConditionModel.mapToLoanCondition() =
        LoanCondition(maxAmount = this.maxAmount, percent = this.percent, period = this.period)

}