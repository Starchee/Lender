package com.starchee.lender.domain.useCases.loan.loanList

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.domain.repositories.LocalLoanRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class GetLocalLoanListUseCaseTest {

    private val localLoanRepository: LocalLoanRepository = mock()

    @Test
    fun `on invoke EXPECTED List of Loan`() {
        val getLocalLoanListUseCase = GetLocalLoanListUseCase(localLoanRepository)
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

        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(expected))

        getLocalLoanListUseCase().test().assertValue(expected)
    }
}