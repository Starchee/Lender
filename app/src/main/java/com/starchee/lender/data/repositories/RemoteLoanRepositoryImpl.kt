package com.starchee.lender.data.repositories

import com.starchee.lender.data.dataSource.loan.RemoteLoanDataSource
import com.starchee.lender.data.models.LoanApplicationModel
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.data.utils.DateUtils
import io.reactivex.Single
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteLoanRepositoryImpl @Inject constructor(
    private val remoteLoanDataSource: RemoteLoanDataSource,
    private val dateUtils: DateUtils
) : RemoteLoanRepository {

    override fun getLoanCondition(token: String): Single<LoanCondition> =
        remoteLoanDataSource.getLoanCondition(token).map { it.mapToLoanCondition() }
            .mapNetworkErrors()

    override fun getLoanList(token: String): Single<List<Loan>> =
        remoteLoanDataSource.getLoanList(token).map { loans ->
            loans.map { it.mapToLoanInfo() }
        }
            .mapNetworkErrors()

    override fun makeLoanApplication(
        token: String,
        loanApplication: LoanApplication,
        loanCondition: LoanCondition,
    ): Single<Loan> =
        remoteLoanDataSource.makeLoanApplication(
            token,
            loanApplication.mapToLoanApplicationModel(loanCondition)
        )
            .map { it.mapToLoanInfo() }.mapNetworkErrors()

    override fun getLoanById(token: String, id: Int): Single<Loan> =
        remoteLoanDataSource.getLoanById(token, id).map { loans -> loans.mapToLoanInfo() }
            .mapNetworkErrors()

    private fun LoanApplication.mapToLoanApplicationModel(loanCondition: LoanCondition) =
        LoanApplicationModel(
            amount = this.amount,
            firstName = this.firstName,
            lastName = this.lastName,
            percent = loanCondition.percent,
            period = loanCondition.period,
            phone = this.phone
        )

    private fun LoanModel.mapToLoanInfo() = Loan(
        amount = this.amount,
        date = dateUtils.formatDate(this.date),
        firstName = this.firstName,
        id = this.id,
        lastName = this.lastName,
        percent = this.percent,
        period = this.period,
        phone = this.phone,
        state = LoanState.valueOf(this.state)
    )

    private fun LoanConditionModel.mapToLoanCondition() =
        LoanCondition(maxAmount = this.maxAmount, percent = this.percent, period = this.period)


    private inline fun <reified T> Single<T>.mapNetworkErrors(): Single<T> =
        this.onErrorResumeNext { error ->
            when (error) {
                is UnknownHostException -> Single.error(NoNetWorkException())
                is HttpException -> mapHttpError(error)
                else -> Single.error(error)
            }
        }

    private fun <T> mapHttpError(error: HttpException): Single<T> =
        when (error.code()) {
            400 -> Single.error(BadRequestException())
            403 -> Single.error(ForbiddenException())
            404 -> Single.error(NotFoundException())
            else -> Single.error(error)
        }
}