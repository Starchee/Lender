package com.starchee.lender.domain.useCases.auth

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.User
import com.starchee.lender.domain.repositories.AuthRepository
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.testRule.TestSchedulerRule
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoginUseCaseTest {

    private val authRepository: AuthRepository = mock()
    private val localRepository: LocalRepository = mock()
    private val user = User("user", "password")


    @Test
    fun `on invoke EXPECT complete`() {
        val loginUseCase = LoginUseCase(authRepository, localRepository)
        val expected = "token"

        Mockito.`when`(authRepository.login(user)).thenReturn(Single.just(expected))

        loginUseCase(user).test().assertComplete()
    }


    @Test
    fun `on invoke login EXPECT save token`() {
        val loginUseCase = LoginUseCase(authRepository, localRepository)
        val token = "token"

        Mockito.`when`(authRepository.login(user)).thenReturn(Single.just(token))

        loginUseCase(user).test()

        verify(localRepository).saveToken(token)
    }


}