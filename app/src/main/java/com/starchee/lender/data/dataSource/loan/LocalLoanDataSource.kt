package com.starchee.lender.data.dataSource.loan

import com.starchee.lender.data.models.LoanModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LocalLoanDataSource {
    fun getLoanList(): Single<List<LoanModel>>
    fun getLoanById(id: Int): Single<LoanModel>
    fun saveLoanList(loanList: List<LoanModel>)
    fun clearCache(): Completable
}

class LocalLoanDataSourceImpl @Inject constructor(private val loanDao: LoanDao): LocalLoanDataSource {

    override fun getLoanList(): Single<List<LoanModel>> = loanDao.getLoanList()

    override fun getLoanById(id: Int): Single<LoanModel> = loanDao.getLoanById(id)

    override fun saveLoanList(loanList: List<LoanModel>) = loanDao.insertLoanList(loanList)

    override fun clearCache(): Completable = loanDao.deleteAllLoans()
}