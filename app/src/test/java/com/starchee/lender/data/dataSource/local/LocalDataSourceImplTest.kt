package com.starchee.lender.data.dataSource.local

import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.starchee.lender.data.dataSource.local.LocalDataSourceImpl.Companion.LOCALE_KEY
import com.starchee.lender.data.dataSource.local.LocalDataSourceImpl.Companion.TOKEN_KEY
import io.reactivex.Emitter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class LocalDataSourceImplTest {

    private val sharedPrefs: SharedPreferences = mock()
    private val editor: SharedPreferences.Editor = mock()
    private val onSharedPrefsChangeListener: SharedPreferences.OnSharedPreferenceChangeListener =
        mock()
    private val emitter: Emitter<String> = mock()

    @Test
    fun `on saveToken EXPECT putString`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)
        val token = "token"

        Mockito.`when`(sharedPrefs.edit()).thenReturn(editor)
        Mockito.`when`(editor.putString(TOKEN_KEY, token)).thenReturn(editor)

        localDataSourceImpl.saveToken(token)

        verify(editor).putString(TOKEN_KEY, token)
    }

    @Test
    fun `on getToken EXPECT token`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)
        val expected = "token"

        Mockito.`when`(sharedPrefs.getString(TOKEN_KEY, "")).thenReturn(expected)

        assertEquals(expected, localDataSourceImpl.getToken())
    }


    @Test
    fun `on isLogin EXPECT true`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)
        val expected = true

        Mockito.`when`(sharedPrefs.contains(TOKEN_KEY)).thenReturn(expected)

        assertEquals(expected, localDataSourceImpl.isLogin())
    }

    @Test
    fun `on isLogin EXPECT false`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)
        val expected = false

        Mockito.`when`(sharedPrefs.contains(TOKEN_KEY)).thenReturn(expected)

        assertEquals(expected, localDataSourceImpl.isLogin())
    }

    @Test
    fun `on clearToken EXPECT remove`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)

        Mockito.`when`(sharedPrefs.edit()).thenReturn(editor)
        Mockito.`when`(editor.remove(TOKEN_KEY)).thenReturn(editor)

        localDataSourceImpl.clearToken()

        verify(editor).remove(any())
    }

    @Test
    fun `on saveLocale EXPECT putString`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)
        val locale = "ru"

        Mockito.`when`(sharedPrefs.edit()).thenReturn(editor)
        Mockito.`when`(editor.putString(LOCALE_KEY, locale)).thenReturn(editor)

        localDataSourceImpl.saveLocale(locale)

        verify(editor).putString(LOCALE_KEY, locale)
    }

    @Test
    fun `on getLocale EXPECT locale`() {
        val localDataSourceImpl =
            LocalDataSourceImpl(sharedPrefs)
        val default = "en"
        val expected = "ru"

        Mockito.`when`(sharedPrefs.getString(LOCALE_KEY, default)).thenReturn(expected)

        localDataSourceImpl.getLocale(default).test().assertValue(expected)

    }
}