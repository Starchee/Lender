package com.starchee.lender.domain.useCases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.domain.entities.Locale
import com.starchee.lender.domain.repositories.LocalRepository
import org.junit.Test

class SaveLocaleUseCaseTest {

    private val localRepository: LocalRepository = mock()

    @Test
    fun `on saveLocale EXPECT saveLocale`() {
        val saveLocaleUseCase =
            SaveLocaleUseCase(localRepository)
        val locale = Locale.RU

        saveLocaleUseCase(locale)

        verify(localRepository).saveLocale(locale)
    }
}