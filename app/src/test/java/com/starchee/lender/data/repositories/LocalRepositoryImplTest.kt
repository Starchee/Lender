package com.starchee.lender.data.repositories

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.data.dataSource.local.LocalDataSource
import com.starchee.lender.domain.entities.Locale
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class LocalRepositoryImplTest {

    private val localDataSource: LocalDataSource = mock()

    @Test
    fun `on saveToken EXPECT saveToken`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)
        val token = "token"

        localRepository.saveToken(token)

        verify(localDataSource).saveToken(token)
    }

    @Test
    fun `on getToken EXPECT token`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)
        val expected = "token"

        Mockito.`when`(localDataSource.getToken()).thenReturn(expected)

        assertEquals(expected, localRepository.getToken())
    }

    @Test
    fun `on isLogin EXPECT true`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)
        val expected = true

        Mockito.`when`(localDataSource.isLogin()).thenReturn(expected)

        assertEquals(expected, localRepository.isLogin())
    }

    @Test
    fun `on isLogin EXPECT false`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)
        val expected = false

        Mockito.`when`(localDataSource.isLogin()).thenReturn(expected)

        assertEquals(expected, localRepository.isLogin())
    }

    @Test
    fun `on clearToken EXPECT clearToken`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)

        localRepository.clearToken()

        verify(localDataSource).clearToken()
    }

    @Test
    fun `on saveLocale EXPECT saveLocale`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)
        val locale = Locale.RU
        val localeLower = "ru"

        localRepository.saveLocale(locale)

        verify(localDataSource).saveLocale(localeLower)
    }

    @Test
    fun `on getLocale EXPECT locale`() {
        val localRepository =
            LocalRepositoryImpl(localDataSource)
        val default = "en"
        val expected = "ru"

        Mockito.`when`(localDataSource.getLocale(default)).thenReturn(
            Flowable.create(
                { emitter -> emitter.onNext(expected) },
                BackpressureStrategy.LATEST
            )
        )

        localRepository.getLocale(default).test().assertValue(expected)

    }
}