package com.starchee.lender.domain.useCases.auth

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.LocalUser
import com.starchee.lender.domain.entities.User
import com.starchee.lender.domain.repositories.AuthRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class RegistrationUseCaseTest {

    private val authRepository: AuthRepository = mock()
    private val localRepository: LocalRepository = mock()
    private val user = User("user", "password")
    private val localUser = LocalUser("user", "USER")

    @Test
    fun `on invoke EXPECT login`() {
        val registrationUseCase = RegistrationUseCase(authRepository, localRepository)

        Mockito.`when`(authRepository.registration(user))
            .thenReturn(Single.just(localUser))

        registrationUseCase(user).test()

        verify(authRepository).login(user)
    }

    @Test
    fun `on invoke EXPECT complete`() {
        val registrationUseCase = RegistrationUseCase(authRepository, localRepository)
        val token = "token"

        Mockito.`when`(authRepository.registration(user))
            .thenReturn(Single.just(localUser))
        Mockito.`when`(authRepository.login(user))
            .thenReturn(Single.just(token))

        registrationUseCase(user).test().assertComplete()
    }

    @Test
    fun `on invoke EXPECT saveToken`() {
        val registrationUseCase = RegistrationUseCase(authRepository, localRepository)
        val token = "token"
        Mockito.`when`(authRepository.registration(user))
            .thenReturn(Single.just(localUser))
        Mockito.`when`(authRepository.login(user))
            .thenReturn(Single.just(token))

        registrationUseCase(user).test()

        verify(localRepository).saveToken(token)
    }
}