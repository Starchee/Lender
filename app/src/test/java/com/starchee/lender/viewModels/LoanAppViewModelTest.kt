package com.starchee.lender.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.repositories.RemoteLoanRepository
import com.starchee.lender.domain.useCases.SaveLocaleUseCase
import com.starchee.lender.domain.useCases.loan.loanApplication.CreateLoanAppUseCase
import com.starchee.lender.domain.useCases.loan.loanApplication.GetLoanConditionUseCase
import com.starchee.lender.testRule.TestSchedulerRule
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoanAppViewModelTest {

    @get:Rule
    val testRule = TestSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val remoteLoanRepository: RemoteLoanRepository = mock()
    private val localRepository: LocalRepository = mock()
    private val createLoanAppUseCase = CreateLoanAppUseCase(remoteLoanRepository, localRepository)
    private val getLoanConditionUseCase =
        GetLoanConditionUseCase(remoteLoanRepository, localRepository)
    private val saveLocaleUseCase = SaveLocaleUseCase(localRepository)
    private val loanCondition = LoanCondition(
        maxAmount = 20000,
        percent = 14.5,
        period = 20,
    )

    private val loanApplication = LoanApplication(
        amount = 20000,
        firstName = "Vasya",
        lastName = "Pupkin",
        phone = "88002000600"
    )

    private val loan = Loan(
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

    @Test
    fun `on saveLocale EXPECT saveLocale`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val locale = Locale.RU

        loanAppViewModel.saveLocate(locale)

        verify(localRepository).saveLocale(locale)
    }

    @Test
    fun `on makeLoanApplication EXPECT true`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<Boolean> = mock()
        loanAppViewModel.loanIsCreated.observeForever(observer)
        val token = "token"
        val expected = true

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(
            remoteLoanRepository.makeLoanApplication(
                token,
                loanApplication,
                loanCondition
            )
        )
            .thenReturn(Single.just(loan))

        loanAppViewModel.makeLoanApplication(loanApplication)

        assertEquals(expected, loanAppViewModel.loanIsCreated.value)
    }

    @Test
    fun `on makeLoanApplication, condition null EXPECT null`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<Boolean> = mock()
        loanAppViewModel.loanIsCreated.observeForever(observer)
        val token = "token"

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(
            remoteLoanRepository.makeLoanApplication(
                token,
                loanApplication,
                loanCondition
            )
        )
            .thenReturn(Single.just(loan))

        loanAppViewModel.makeLoanApplication(loanApplication)

        assertNull(loanAppViewModel.loanIsCreated.value)
    }

    @Test
    fun `on makeLoanApplication EXPECT on error NETWORK`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanAppViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NoNetWorkException()
        val expected = NetworkError.NETWORK

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(
            remoteLoanRepository.makeLoanApplication(
                token,
                loanApplication,
                loanCondition
            )
        )
            .thenReturn(Single.error(exception))

        loanAppViewModel.makeLoanApplication(loanApplication)

        assertEquals(expected, loanAppViewModel.networkErrors.value)
    }

    @Test
    fun `on makeLoanApplication EXPECT on error BAD_REQUEST`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanAppViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = BadRequestException()
        val expected = NetworkError.BAD_REQUEST

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(
            remoteLoanRepository.makeLoanApplication(
                token,
                loanApplication,
                loanCondition
            )
        )
            .thenReturn(Single.error(exception))

        loanAppViewModel.makeLoanApplication(loanApplication)

        assertEquals(expected, loanAppViewModel.networkErrors.value)
    }

    @Test
    fun `on makeLoanApplication EXPECT on error NOT_FOUND`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanAppViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NotFoundException()
        val expected = NetworkError.NOT_FOUND

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(
            remoteLoanRepository.makeLoanApplication(
                token,
                loanApplication,
                loanCondition
            )
        )
            .thenReturn(Single.error(exception))

        loanAppViewModel.makeLoanApplication(loanApplication)

        assertEquals(expected, loanAppViewModel.networkErrors.value)
    }

    @Test
    fun `on validate max EXPECT null`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<InputAppError> = mock()
        loanAppViewModel.inputErrors.observeForever(observer)
        val token = "token"

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        val input = loanCondition.maxAmount

        loanAppViewModel.validateAmount(input)

        assertNull(loanAppViewModel.inputErrors.value)
    }

    @Test
    fun `on validate min EXPECT null`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<InputAppError> = mock()
        loanAppViewModel.inputErrors.observeForever(observer)
        val token = "token"

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        val input = 0

        loanAppViewModel.validateAmount(input)

        assertNull(loanAppViewModel.inputErrors.value)
    }

    @Test
    fun `on validate above EXPECT MAX_AMOUNT_ERROR`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<InputAppError> = mock()
        loanAppViewModel.inputErrors.observeForever(observer)
        val token = "token"

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(loanCondition))
        loanAppViewModel.loadOffer()

        val input = loanCondition.maxAmount + 1

        val expected = InputAppError.MAX_AMOUNT_ERROR

        loanAppViewModel.validateAmount(input)

        assertEquals(expected, loanAppViewModel.inputErrors.value)
    }

    @Test
    fun `on validate max, condition null  EXPECT null`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<InputAppError> = mock()
        loanAppViewModel.inputErrors.observeForever(observer)

        val input = loanCondition.maxAmount

        loanAppViewModel.validateAmount(input)

        assertNull(loanAppViewModel.inputErrors.value)
    }

    @Test
    fun `on loanOffer EXPECT LoanCondition`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<LoanCondition> = mock()
        loanAppViewModel.loanOffers.observeForever(observer)
        val token = "token"
        val expected = loanCondition

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.just(expected))

        loanAppViewModel.loadOffer()

        assertEquals(expected, loanAppViewModel.loanOffers.value)
    }

    @Test
    fun `on loanOffer EXPECT on error NETWORK`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanAppViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NoNetWorkException()
        val expected = NetworkError.NETWORK

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        loanAppViewModel.loadOffer()

        assertEquals(expected, loanAppViewModel.networkErrors.value)
    }

    @Test
    fun `on loanOffer EXPECT on error BAD_REQUEST`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanAppViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = BadRequestException()
        val expected = NetworkError.BAD_REQUEST

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        loanAppViewModel.loadOffer()

        assertEquals(expected, loanAppViewModel.networkErrors.value)
    }

    @Test
    fun `on loanOffer EXPECT on error NOT_FOUND`() {
        val loanAppViewModel =
            LoanAppViewModel(createLoanAppUseCase, getLoanConditionUseCase, saveLocaleUseCase)
        val observer: Observer<NetworkError> = mock()
        loanAppViewModel.networkErrors.observeForever(observer)
        val token = "token"
        val exception = NotFoundException()
        val expected = NetworkError.NOT_FOUND

        Mockito.`when`(localRepository.getToken()).thenReturn(token)
        Mockito.`when`(remoteLoanRepository.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        loanAppViewModel.loadOffer()

        assertEquals(expected, loanAppViewModel.networkErrors.value)
    }
}