package com.starchee.lender.data.dataSource.loan

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class LocalLoanDataSourceImplTest {

    private val loanDao: LoanDao = mock()

    @Test
    fun `on getLoanList EXPECT List of LoanModel`() {
        val localLoanDataSource = LocalLoanDataSourceImpl(loanDao)
        val expected = listOf(
            LoanModel(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = "APPROVED"
            ),
            LoanModel(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REGISTERED"
            ),
            LoanModel(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REJECTED"
            )
        )

        Mockito.`when`(loanDao.getLoanList()).thenReturn(Single.just(expected))

        localLoanDataSource.getLoanList().test().assertValue(expected)
    }

    @Test
    fun `on getLoanById EXPECT LoanModel`() {
        val localLoanDataSource = LocalLoanDataSourceImpl(loanDao)
        val id = 100
        val expected = LoanModel(
            amount = 20000,
            date = "06.12.2020 12:20",
            firstName = "Vasya",
            id = id,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = "APPROVED"
        )

        Mockito.`when`(loanDao.getLoanById(id)).thenReturn(Single.just(expected))

        localLoanDataSource.getLoanById(id).test().assertValue(expected)
    }

    @Test
    fun `on saveLoanList EXPECT insertLoanList List of LoanModel`() {
        val localLoanDataSource = LocalLoanDataSourceImpl(loanDao)
        val loanList = listOf(
            LoanModel(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = "APPROVED"
            ),
            LoanModel(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REGISTERED"
            ),
            LoanModel(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REJECTED"
            )
        )

        localLoanDataSource.saveLoanList(loanList)

        verify(loanDao).insertLoanList(loanList)
    }

    @Test
    fun `on clearCache EXPECT complete`() {
        val localLoanDataSource = LocalLoanDataSourceImpl(loanDao)

        Mockito.`when`(loanDao.deleteAllLoans()).thenReturn(Completable.complete())

        localLoanDataSource.clearCache().test().assertComplete()
    }
}