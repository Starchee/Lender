package com.starchee.lender.data.dataSource.auth

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.data.models.LocalUserModel
import com.starchee.lender.data.models.UserModel
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mockito

class AuthDataSourceImplTest {

    private val userModel = UserModel("user", "password")
    private val authApi: AuthApi = mock()

    @Test
    fun `on login EXPECT token`() {
        val authDataSource = AuthDataSourceImpl(authApi)
        val expected = "token"

        Mockito.`when`(authApi.login(userModel)).thenReturn(Single.just(expected))

        authDataSource.login(userModel).test().assertValue(expected)
    }

    @Test
    fun `on registration EXPECT LocalUserModel`() {
        val authDataSource = AuthDataSourceImpl(authApi)
        val expected = LocalUserModel("user", "USER")

        Mockito.`when`(authApi.registration(userModel)).thenReturn(Single.just(expected))

        authDataSource.registration(userModel).test().assertValue(expected)
    }
}