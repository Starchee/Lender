package com.starchee.lender.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.repositories.LocalRepository
import com.starchee.lender.domain.useCases.GetSavedLocaleUseCase
import com.starchee.lender.testRule.TestSchedulerRule
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainActivityViewModelTest {

    @get:Rule
    val testRule = TestSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val localRepository: LocalRepository = mock()
    private val getSavedLocaleUseCase = GetSavedLocaleUseCase(localRepository)

    @Test
    fun `on invoke EXPECT ru`() {
        val mainActivityViewModel =
            MainActivityViewModel(getSavedLocaleUseCase)
        val observer: Observer<String> = mock()
        mainActivityViewModel.locale.observeForever(observer)
        val default = "en"
        val expected = "ru"

        Mockito.`when`(localRepository.getLocale(default)).thenReturn(
            Flowable.create(
                { emitter -> emitter.onNext(expected) },
                BackpressureStrategy.LATEST
            )
        )

        mainActivityViewModel.getLocale(default)

        Assert.assertEquals(expected, mainActivityViewModel.locale.value)
    }
}