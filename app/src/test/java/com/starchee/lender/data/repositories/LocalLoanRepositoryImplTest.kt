package com.starchee.lender.data.repositories

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.data.dataSource.loan.LocalLoanDataSource
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import com.starchee.lender.data.utils.DateUtils
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.entities.LoanState
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class LocalLoanRepositoryImplTest {

    private val localLoanDataSource: LocalLoanDataSource = mock()
    private val dateUtils: DateUtils = DateUtils()

    @Test
    fun `on saveCondition EXPECTED insertLoanList`() {
        val localLoanRepository =
            LocalLoanRepositoryImpl(localLoanDataSource, dateUtils)
        val loanModelList = listOf(
            LoanModel(
                amount = 20000,
                date = "07.12.2020 00:07",
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
                date = "07.12.2020 00:07",
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
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REJECTED"
            )
        )
        val loanList = listOf(
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = LoanState.APPROVED
            ),
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REGISTERED
            ),
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REJECTED
            )
        )

        localLoanRepository.saveLoanList(loanList)

        verify(localLoanDataSource).saveLoanList(loanModelList)
    }

    @Test
    fun `on saveCondition EXPECTED List of Loan`() {
        val localLoanRepository =
            LocalLoanRepositoryImpl(localLoanDataSource, dateUtils)
        val loanModelList = listOf(
            LoanModel(
                amount = 20000,
                date = "07.12.2020 00:07",
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
                date = "07.12.2020 00:07",
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
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REJECTED"
            )
        )
        val expected = listOf(
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = LoanState.APPROVED
            ),
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REGISTERED
            ),
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REJECTED
            )
        )

        Mockito.`when`(localLoanDataSource.getLoanList()).thenReturn(Single.just(loanModelList))

        localLoanRepository.getLoanList().test().assertValue(expected)
    }

    @Test
    fun `on getLoanById EXPECT Loan`() {
        val localLoanRepository =
            LocalLoanRepositoryImpl(localLoanDataSource, dateUtils)
        val id = 100
        val loanModel = LoanModel(
            amount = 20000,
            date = "07.12.2020 00:07",
            firstName = "Vasya",
            id = id,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = "APPROVED"
        )
        val expected = Loan(
            amount = 20000,
            date = "07.12.2020 00:07",
            firstName = "Vasya",
            id = id,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = LoanState.APPROVED
        )

        Mockito.`when`(localLoanDataSource.getLoanById(id))
            .thenReturn(Single.just(loanModel))

        localLoanRepository.getLoanById(id).test().assertValue(expected)
    }

    @Test
    fun `on saveLoanList EXPECT insertLoanList List of LoanModel`() {
        val localLoanRepository =
            LocalLoanRepositoryImpl(localLoanDataSource, dateUtils)
        val loanModelList = listOf(
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
        val loanList = listOf(
            Loan(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = LoanState.APPROVED
            ),
            Loan(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REGISTERED
            ),
            Loan(
                amount = 20000,
                date = "06.12.2020 12:20",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REJECTED
            )
        )

        localLoanRepository.saveLoanList(loanList)

        verify(localLoanDataSource).saveLoanList(loanModelList)
    }

    @Test
    fun `on clearCache EXPECT clearCache`() {
        val localLoanRepository =
            LocalLoanRepositoryImpl(localLoanDataSource, dateUtils)

        localLoanRepository.clearCache()

        verify(localLoanDataSource).clearCache()
    }

}