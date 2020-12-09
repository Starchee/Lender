package com.starchee.lender.domain.useCases.loanInfo

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.entities.Loan
import com.starchee.lender.domain.entities.LoanState
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class GetRemoteLoanUseCaseTest {

    private val remoteLoanRepository: RemoteLoanRepository = mock()
    private val localRepository: LocalRepository = mock()

    @Test
    fun `on invoke EXPECT Loan`() {
        val getRemoteLoanUserCase = GetRemoteLoanUseCase(remoteLoanRepository, localRepository)
        val id = 300
        val token = "token"
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

        Mockito.`when`(localRepository.getToken())
            .thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, id))
            .thenReturn(Single.just(expected))

        getRemoteLoanUserCase(id).test().assertValue(expected)
    }
}