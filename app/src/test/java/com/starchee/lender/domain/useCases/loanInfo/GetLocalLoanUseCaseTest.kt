package com.starchee.lender.domain.useCases.loanInfo

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.domain.repositories.LocalLoanRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class GetLocalLoanUseCaseTest {

    private val localLoanRepository: LocalLoanRepository = mock()

    @Test
    fun `on invoke EXPECT Loan`() {
        val getLocalLoanUseCase = GetLocalLoanUseCase(localLoanRepository)
        val id = 300
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

        Mockito.`when`(localLoanRepository.getLoanById(id))
            .thenReturn(Single.just(expected))

        getLocalLoanUseCase(id).test().assertValue(expected)
    }
}