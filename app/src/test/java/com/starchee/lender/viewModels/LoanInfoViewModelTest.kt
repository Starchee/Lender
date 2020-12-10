package com.starchee.lender.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.useCases.loanInfo.GetLocalLoanUseCase
import com.starchee.lender.domain.useCases.loanInfo.GetRemoteLoanUseCase
import com.starchee.lender.testRule.TestSchedulerRule
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoanInfoViewModelTest {

    @get:Rule
    val testRule = TestSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val localLoanRepository: LocalLoanRepository = mock()
    private val remoteLoanRepository: RemoteLoanRepository = mock()
    private val localRepository: LocalRepository = mock()
    private val getRemoteLoanUseCase = GetRemoteLoanUseCase(remoteLoanRepository, localRepository)
    private val getLocalLoanUseCase = GetLocalLoanUseCase(localLoanRepository)
    private val loan = Loan(
        amount = 20000,
        date = "07.12.2020 00:07",
        firstName = "Vasya",
        id = 300,
        lastName = "Pupkin",
        percent = 14.5,
        period = 20,
        phone = "88002000600",
        state = LoanState.APPROVED
    )

    @Test
    fun `on getLoanById EXPECT List of Loan`() {
        val loanInfoViewModel = LoanInfoViewModel(getRemoteLoanUseCase, getLocalLoanUseCase)
        val observer: Observer<Loan> = mock()
        loanInfoViewModel.loan.observeForever(observer)
        val token = "token"
        val expected = loan

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, loan.id))
            .thenReturn(Single.just(expected))
        Mockito.`when`(localLoanRepository.getLoanById(loan.id)).thenReturn(Single.just(expected))

        loanInfoViewModel.getLoanById(loan.id)

        assertEquals(expected, loanInfoViewModel.loan.value)
    }

    @Test
    fun `on getLoanById EXPECT on error NETWORK`() {
        val loanInfoViewModel = LoanInfoViewModel(getRemoteLoanUseCase, getLocalLoanUseCase)
        val observer: Observer<NetworkError> = mock()
        loanInfoViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NoNetWorkException()
        val expected = NetworkError.NETWORK

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, loan.id))
            .thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanById(loan.id)).thenReturn(Single.just(loan))

        loanInfoViewModel.getLoanById(loan.id)

        assertEquals(expected, loanInfoViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanById EXPECT on error BAD_REQUEST`() {
        val loanInfoViewModel = LoanInfoViewModel(getRemoteLoanUseCase, getLocalLoanUseCase)
        val observer: Observer<NetworkError> = mock()
        loanInfoViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = BadRequestException()
        val expected = NetworkError.BAD_REQUEST

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, loan.id))
            .thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanById(loan.id)).thenReturn(Single.just(loan))

        loanInfoViewModel.getLoanById(loan.id)

        assertEquals(expected, loanInfoViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanById EXPECT on error NOT_FOUND`() {
        val loanInfoViewModel = LoanInfoViewModel(getRemoteLoanUseCase, getLocalLoanUseCase)
        val observer: Observer<NetworkError> = mock()
        loanInfoViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NotFoundException()
        val expected = NetworkError.NOT_FOUND

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, loan.id))
            .thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanById(loan.id)).thenReturn(Single.just(loan))

        loanInfoViewModel.getLoanById(loan.id)

        assertEquals(expected, loanInfoViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanById EXPECT on error FORBIDDEN`() {
        val loanInfoViewModel = LoanInfoViewModel(getRemoteLoanUseCase, getLocalLoanUseCase)
        val observer: Observer<NetworkError> = mock()
        loanInfoViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = ForbiddenException()
        val expected = NetworkError.FORBIDDEN

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, loan.id))
            .thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanById(loan.id)).thenReturn(Single.just(loan))

        loanInfoViewModel.getLoanById(loan.id)

        assertEquals(expected, loanInfoViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanById EXPECT on error get local loan`() {
        val loanInfoViewModel = LoanInfoViewModel(getRemoteLoanUseCase, getLocalLoanUseCase)
        val observer: Observer<Loan> = mock()
        loanInfoViewModel.loan.observeForever(observer)
        val token = "token"
        val exception = NoNetWorkException()
        val expected = loan

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanById(token, loan.id))
            .thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanById(loan.id)).thenReturn(Single.just(loan))

        loanInfoViewModel.getLoanById(loan.id)

        assertEquals(expected, loanInfoViewModel.loan.value)
    }
}