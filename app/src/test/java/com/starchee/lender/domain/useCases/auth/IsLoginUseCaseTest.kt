package com.starchee.lender.domain.useCases.auth

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.repositories.LocalRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class IsLoginUseCaseTest {

    private val localRepository: LocalRepository = mock()

    @Test
    fun `on invoke EXPECT true`() {
        val isLoginUseCase = IsLoginUseCase(localRepository)
        val expected = true

        Mockito.`when`(localRepository.isLogin()).thenReturn(expected)

        assertEquals(expected, isLoginUseCase())
    }

    @Test
    fun `on invoke EXPECT false`() {
        val isLoginUseCase = IsLoginUseCase(localRepository)
        val expected = false

        Mockito.`when`(localRepository.isLogin()).thenReturn(expected)

        assertEquals(expected, isLoginUseCase())
    }
}