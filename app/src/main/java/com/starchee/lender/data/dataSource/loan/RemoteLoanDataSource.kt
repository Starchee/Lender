package com.starchee.lender.data.dataSource.loan

import com.starchee.lender.data.models.LoanApplicationModel
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import io.reactivex.Single
import javax.inject.Inject

interface RemoteLoanDataSource {
    fun getLoanCondition(token: String): Single<LoanConditionModel>
    fun getLoanList(token: String): Single<List<LoanModel>>
    fun makeLoanApplication(token: String, loansApplicationModel: LoanApplicationModel): Single<LoanModel>
    fun getLoanById(token: String, id: Int): Single<LoanModel>
}

class RemoteLoanDataSourceImpl @Inject constructor(private val loanApi: LoanApi) :
    RemoteLoanDataSource {

    override fun getLoanCondition(token: String): Single<LoanConditionModel> =
        loanApi.getLoanCondition(token)

    override fun getLoanList(token: String): Single<List<LoanModel>> = loanApi.getLoanList(token)

    override fun makeLoanApplication(
        token: String,
        loansApplicationModel: LoanApplicationModel
    ): Single<LoanModel> =
        loanApi.makeLoanApplication(
            token,
            loansApplicationModel
        )

    override fun getLoanById(token: String, id: Int): Single<LoanModel> =
        loanApi.getLoanById(token, id)

}