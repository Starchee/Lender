package com.starchee.lender.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.repositories.AuthRepository
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.useCases.auth.LoginUseCase
import com.starchee.lender.domain.useCases.auth.LogoutUseCase
import com.starchee.lender.domain.useCases.auth.RegistrationUseCase
import com.starchee.lender.testRule.TestSchedulerRule
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class AuthViewModelTest {

    @get:Rule
    val testRule = TestSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val authRepository: AuthRepository = mock()
    private val localRepository: LocalRepository = mock()
    private val localLoanRepository: LocalLoanRepository = mock()
    private val loginUseCase = LoginUseCase(authRepository, localRepository)
    private val registrationUseCase = RegistrationUseCase(authRepository, localRepository)
    private val logoutUseCase = LogoutUseCase(localLoanRepository, localRepository)
    private val user = User("user", "password")
    private val localUser = LocalUser("user", "USER")

    @Test
    fun `on logout EXPECT LOGOUT`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<AuthState> = mock()
        authViewModel.isLogin.observeForever(observer)
        val expected = AuthState.LOGOUT

        Mockito.`when`(localLoanRepository.clearCache()).thenReturn(Completable.complete())

        authViewModel.logout()

        assertEquals(expected, authViewModel.isLogin.value)
    }

    @Test
    fun `on login EXPECT LOGIN`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<AuthState> = mock()
        authViewModel.isLogin.observeForever(observer)
        val token = "token"
        val expected = AuthState.LOGIN

        Mockito.`when`(authRepository.login(user)).thenReturn(Single.just(token))

        authViewModel.login(user.name, user.password)

        assertEquals(expected, authViewModel.isLogin.value)
    }

    @Test
    fun `on login EXPECT on error NETWORK`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<NetworkError> = mock()
        authViewModel.networkErrors.observeForever(observer)
        val exception = NoNetWorkException()
        val expected = NetworkError.NETWORK

        Mockito.`when`(authRepository.login(user)).thenReturn(Single.error(exception))

        authViewModel.login(user.name, user.password)

        assertEquals(expected, authViewModel.networkErrors.value)
    }

    @Test
    fun `on login EXPECT on error BAD_REQUEST`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<NetworkError> = mock()
        authViewModel.networkErrors.observeForever(observer)
        val exception = BadRequestException()
        val expected = NetworkError.BAD_REQUEST

        Mockito.`when`(authRepository.login(user)).thenReturn(Single.error(exception))

        authViewModel.login(user.name, user.password)

        assertEquals(expected, authViewModel.networkErrors.value)
    }

    @Test
    fun `on login EXPECT on error NOT_FOUND `() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<NetworkError> = mock()
        authViewModel.networkErrors.observeForever(observer)
        val exception = NotFoundException()
        val expected = NetworkError.NOT_FOUND

        Mockito.`when`(authRepository.login(user)).thenReturn(Single.error(exception))

        authViewModel.login(user.name, user.password)

        assertEquals(expected, authViewModel.networkErrors.value)
    }

    @Test
    fun `on registration EXPECT REGISTERED`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<AuthState> = mock()
        authViewModel.isLogin.observeForever(observer)
        val token = "token"
        val expected = AuthState.REGISTERED

        Mockito.`when`(authRepository.registration(user)).thenReturn(Single.just(localUser))
        Mockito.`when`(authRepository.login(user)).thenReturn(Single.just(token))

        authViewModel.registration(user.name, user.password)

        assertEquals(expected, authViewModel.isLogin.value)
    }

    @Test
    fun `on registration EXPECT on error NETWORK`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<NetworkError> = mock()
        authViewModel.networkErrors.observeForever(observer)
        val exception = NoNetWorkException()
        val expected = NetworkError.NETWORK

        Mockito.`when`(authRepository.registration(user)).thenReturn(Single.error(exception))

        authViewModel.registration(user.name, user.password)

        assertEquals(expected, authViewModel.networkErrors.value)
    }

    @Test
    fun `on registration EXPECT on error BAD_REQUEST`() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<NetworkError> = mock()
        authViewModel.networkErrors.observeForever(observer)
        val exception = BadRequestException()
        val expected = NetworkError.BAD_REQUEST

        Mockito.`when`(authRepository.registration(user)).thenReturn(Single.error(exception))

        authViewModel.registration(user.name, user.password)

        assertEquals(expected, authViewModel.networkErrors.value)
    }

    @Test
    fun `on registration EXPECT on error NOT_FOUND `() {
        val authViewModel = AuthViewModel(loginUseCase, registrationUseCase, logoutUseCase)
        val observer: Observer<NetworkError> = mock()
        authViewModel.networkErrors.observeForever(observer)
        val exception = NotFoundException()
        val expected = NetworkError.NOT_FOUND

        Mockito.`when`(authRepository.registration(user)).thenReturn(Single.error(exception))

        authViewModel.registration(user.name, user.password)

        assertEquals(expected, authViewModel.networkErrors.value)
    }
}