package com.starchee.lender.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.useCases.auth.IsLoginUseCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class NavigationViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val localRepository: LocalRepository = mock()
    private val isLoginUseCase = IsLoginUseCase(localRepository)
    private val observer: Observer<Boolean> = mock()

    @Test
    fun `on isLogin EXPECT true`() {
        val expected = true
        Mockito.`when`(localRepository.isLogin()).thenReturn(expected)
        val navigationViewModel = NavigationViewModel(isLoginUseCase)

        navigationViewModel.isLogin.observeForever(observer)

        Assert.assertEquals(expected, navigationViewModel.isLogin.value)
    }

    @Test
    fun `on isLogin EXPECT false`() {
        val expected = false
        Mockito.`when`(localRepository.isLogin()).thenReturn(expected)
        val navigationViewModel = NavigationViewModel(isLoginUseCase)

        navigationViewModel.isLogin.observeForever(observer)

        Assert.assertEquals(expected, navigationViewModel.isLogin.value)
    }
}