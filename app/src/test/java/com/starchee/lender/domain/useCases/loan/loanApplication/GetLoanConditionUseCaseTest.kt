package com.starchee.lender.domain.useCases.loan.loanApplication

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanApplication
import com.starchee.lender.domain.entities.LoanCondition
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class GetLoanConditionUseCaseTest {

    private val remoteLoanRepository: RemoteLoanRepository = mock()
    private val localRepository: LocalRepository = mock()

    @Test
    fun `on invoke EXPECT LoanCondition`() {
        val getLoanConditionUseCase =
            GetLoanConditionUseCase(remoteLoanRepository, localRepository)
        val token = "token"
        val expected = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )

        Mockito.`when`(localRepository.getToken())
            .thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(expected))

        getLoanConditionUseCase().test().assertValue(expected)

    }
}