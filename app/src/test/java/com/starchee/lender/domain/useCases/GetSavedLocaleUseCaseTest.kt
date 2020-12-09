package com.starchee.lender.domain.useCases

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.domain.repositories.LocalRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.junit.Test
import org.mockito.Mockito

class GetSavedLocaleUseCaseTest {

    private val localRepository: LocalRepository = mock()

    @Test
    fun `on invoke EXPECT locale`() {
        val getSavedLocaleUseCase =
            GetSavedLocaleUseCase(localRepository)
        val default = "en"
        val expected = "ru"

        Mockito.`when`(localRepository.getLocale(default)).thenReturn(
            Flowable.create(
                { emitter -> emitter.onNext(expected) },
                BackpressureStrategy.LATEST
            )
        )

        getSavedLocaleUseCase(default).test().assertValue(expected)
    }
}