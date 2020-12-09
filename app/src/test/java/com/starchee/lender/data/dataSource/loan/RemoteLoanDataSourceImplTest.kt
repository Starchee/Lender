package com.starchee.lender.data.dataSource.loan

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.data.models.LoanApplicationModel
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class RemoteLoanDataSourceImplTest {

    private val token = "token"
    private val loanApi: LoanApi = mock()

    @Test
    fun `on getLoanCondition EXPECT LoanConditionModel`() {
        val remoteLoanDataSource = RemoteLoanDataSourceImpl(loanApi)
        val expected = LoanConditionModel(maxAmount = 20000, period = 20, percent = 14.5)

        Mockito.`when`(loanApi.getLoanCondition(token)).thenReturn(Single.just(expected))

        remoteLoanDataSource.getLoanCondition(token).test().assertValue(expected)
    }

    @Test
    fun `on getLoanList EXPECT List of LoanModel`() {
        val remoteLoanDataSource = RemoteLoanDataSourceImpl(loanApi)
        val expected = listOf(
            LoanModel(
                amount = 20000,
                date = "2020-12-06T17:07:32.431+00:00",
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
                date = "2020-12-06T17:07:32.431+00:00",
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
                date = "2020-12-06T17:07:32.431+00:00",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REJECTED"
            )
        )

        Mockito.`when`(loanApi.getLoanList(token)).thenReturn(Single.just(expected))

        remoteLoanDataSource.getLoanList(token).test().assertValue(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECT LoanModel`() {
        val remoteLoanDataSource = RemoteLoanDataSourceImpl(loanApi)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val expected = LoanModel(
            amount = 20000,
            date = "2020-12-06T17:07:32.431+00:00",
            firstName = "Vasya",
            id = 100,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = "APPROVED"
        )

        Mockito.`when`(loanApi.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.just(expected))

        remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel).test()
            .assertValue(expected)
    }

    @Test
    fun `on getLoanInfoById EXPECT LoanModel`() {
        val remoteLoanDataSource = RemoteLoanDataSourceImpl(loanApi)
        val id = 100
        val expected = LoanModel(
            amount = 20000,
            date = "2020-12-06T17:07:32.431+00:00",
            firstName = "Vasya",
            id = id,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = "APPROVED"
        )

        Mockito.`when`(loanApi.getLoanById(token, id)).thenReturn(Single.just(expected))

        remoteLoanDataSource.getLoanById(token, id).test().assertValue(expected)
    }
}