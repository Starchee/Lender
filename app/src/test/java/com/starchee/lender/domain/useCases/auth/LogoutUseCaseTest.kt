package com.starchee.lender.domain.useCases.auth

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.repositories.LocalLoanRepository
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.Completable
import org.junit.Test
import org.mockito.Mockito

class LogoutUseCaseTest {

    private val localLoanRepository: LocalLoanRepository = mock()
    private val localRepository: LocalRepository = mock()


    @Test
    fun `on invoke EXPECT clearCache`() {
        val logoutUseCase = LogoutUseCase(localLoanRepository, localRepository)

        Mockito.`when`(localLoanRepository.clearCache()).thenReturn(Completable.complete())

        logoutUseCase().test().assertComplete()
    }


    @Test
    fun `on invoke EXPECT clearToken`() {
        val logoutUseCase = LogoutUseCase(localLoanRepository, localRepository)
        Mockito.`when`(localLoanRepository.clearCache()).thenReturn(Completable.complete())

        logoutUseCase().test()

        verify(localRepository).clearToken()
    }
}