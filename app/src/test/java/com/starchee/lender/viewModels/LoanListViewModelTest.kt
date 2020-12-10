package com.starchee.lender.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.useCases.SaveLocaleUseCase
import com.starchee.lender.domain.useCases.loan.loanList.GetLocalLoanListUseCase
import com.starchee.lender.domain.useCases.loan.loanList.GetRemoteLoanListUseCase
import com.starchee.lender.testRule.TestSchedulerRule
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoanListViewModelTest {

    @get:Rule
    val testRule = TestSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val localLoanRepository: LocalLoanRepository = mock()
    private val remoteLoanRepository: RemoteLoanRepository = mock()
    private val localRepository: LocalRepository = mock()
    private val getRemoteLoanListUseCase =
        GetRemoteLoanListUseCase(remoteLoanRepository, localLoanRepository, localRepository)
    private val getLocalLoanListUseCase = GetLocalLoanListUseCase(localLoanRepository)
    private val saveLocaleUseCase = SaveLocaleUseCase(localRepository)
    private val loanList = listOf(
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

    @Test
    fun `on saveLocale EXPECT saveLocale`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val locale = Locale.RU

        loanListViewModel.saveLocate(locale)

        verify(localRepository).saveLocale(locale)
    }

    @Test
    fun `on getLoanList EXPECT List of Loan`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val observer: Observer<List<Loan>> = mock()
        loanListViewModel.loanList.observeForever(observer)
        val token = "token"
        val expected = loanList

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanList(token)).thenReturn(Single.just(expected))
        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(loanList))

        loanListViewModel.getLoanList()

        assertEquals(expected, loanListViewModel.loanList.value)
    }

    @Test
    fun `on getLoanList EXPECT on error NETWORK`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanListViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NoNetWorkException()
        val expected = NetworkError.NETWORK

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanList(token)).thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(loanList))

        loanListViewModel.getLoanList()

        assertEquals(expected, loanListViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanList EXPECT on error BAD_REQUEST`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanListViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = BadRequestException()
        val expected = NetworkError.BAD_REQUEST

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanList(token)).thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(loanList))

        loanListViewModel.getLoanList()

        assertEquals(expected, loanListViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanList EXPECT on error NOT_FOUND`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanListViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NotFoundException()
        val expected = NetworkError.NOT_FOUND

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanList(token)).thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(loanList))

        loanListViewModel.getLoanList()

        assertEquals(expected, loanListViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanList EXPECT on error FORBIDDEN`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanListViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = ForbiddenException()
        val expected = NetworkError.FORBIDDEN

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanList(token)).thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(loanList))

        loanListViewModel.getLoanList()

        assertEquals(expected, loanListViewModel.networkErrors.value)
    }

    @Test
    fun `on getLoanList EXPECT on error get local loan list`() {
        val loanListViewModel =
            LoanListViewModel(getRemoteLoanListUseCase, getLocalLoanListUseCase, saveLocaleUseCase)
        val observer: Observer<List<Loan>> = mock()
        loanListViewModel.loanList.observeForever(observer)
        val token = "token"
        val exception = NoNetWorkException()
        val expected = loanList

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanList(token)).thenReturn(Single.error(exception))
        Mockito.`when`(localLoanRepository.getLoanList()).thenReturn(Single.just(loanList))

        loanListViewModel.getLoanList()

        assertEquals(expected, loanListViewModel.loanList.value)
    }
}