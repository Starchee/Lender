package com.starchee.lender.domain.useCases.loan.loanApplication

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanApplication
import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class CreateLoanAppUseCaseTest {

    private val remoteLoanRepository: RemoteLoanRepository = mock()
    private val localRepository: LocalRepository = mock()


    @Test
    fun `on invoke EXPECT complete`() {
        val createLoanAppUseCase =
            CreateLoanAppUseCase(remoteLoanRepository, localRepository)
        val token = "token"
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )
        val loan = Loan(
            amount = 20000,
            date = "07.12.2020 00:07",
            firstName = "Vasya",
            id = 100,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = LoanState.APPROVED
        )

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(
            remoteLoanRepository.makeLoanApplication(
                token,
                loanApplication,
                loanCondition
            )
        )
            .thenReturn(Single.just(loan))

        createLoanAppUseCase(loanApplication, loanCondition).test().assertComplete()
    }
}