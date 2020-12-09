package com.starchee.lender.data.repositories

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.data.dataSource.auth.AuthApi
import com.starchee.lender.data.dataSource.auth.AuthDataSource
import com.starchee.lender.data.dataSource.auth.AuthDataSourceImpl
import com.starchee.lender.data.models.LocalUserModel
import com.starchee.lender.data.models.UserModel
import com.starchee.lender.domain.entities.*
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class AuthRepositoryImplTest {

    private val authDataSource: AuthDataSource = mock()

    private val user = User("user", "password")
    private val userModel = UserModel("user", "password")


    @Test
    fun `on login EXPECT token`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val expected = "token"

        Mockito.`when`(authDataSource.login(userModel)).thenReturn(Single.just(expected))

        authRepositoryImpl.login(user).test().assertValue(expected)
    }

    @Test
    fun `on login EXPECT Throwable`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val expected = Throwable()

        Mockito.`when`(authDataSource.login(userModel)).thenReturn(Single.error(expected))

        authRepositoryImpl.login(user).test().assertError(expected)
    }

    @Test
    fun `on login EXPECT NoNetWorkException`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val exception = UnknownHostException()
        val expected = NoNetWorkException::class.java

        Mockito.`when`(authDataSource.login(userModel)).thenReturn(Single.error(exception))

        authRepositoryImpl.login(user).test().assertError(expected)
    }

    @Test
    fun `on login EXPECT BadRequestException`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val exception = HttpException(Response.error<Any>(400,  "BAD REQUEST".toResponseBody("text/plain".toMediaTypeOrNull())))
        val expected = BadRequestException::class.java

        Mockito.`when`(authDataSource.login(userModel)).thenReturn(Single.error(exception))

        authRepositoryImpl.login(user).test().assertError(expected)
    }

    @Test
    fun `on login EXPECT NotFoundException`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val exception = HttpException(Response.error<Any>(404,  "NOT FOUND".toResponseBody("text/plain".toMediaTypeOrNull())))
        val expected = NotFoundException::class.java

        Mockito.`when`(authDataSource.login(userModel)).thenReturn(Single.error(exception))

        authRepositoryImpl.login(user).test().assertError(expected)
    }

    @Test
    fun `on registration EXPECT LocalUserModel`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val localUserModel = LocalUserModel("user", "USER")
        val expected = LocalUser("user", "USER")

        Mockito.`when`(authDataSource.registration(userModel))
            .thenReturn(Single.just(localUserModel))

        authRepositoryImpl.registration(user).test().assertValue(expected)
    }

    @Test
    fun `on registration EXPECT Throwable`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val expected = Throwable()

        Mockito.`when`(authDataSource.registration(userModel)).thenReturn(Single.error(expected))

        authRepositoryImpl.registration(user).test().assertError(expected)
    }

    @Test
    fun `on registration EXPECT NoNetWorkException`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val exception = UnknownHostException()
        val expected = NoNetWorkException::class.java

        Mockito.`when`(authDataSource.registration(userModel)).thenReturn(Single.error(exception))

        authRepositoryImpl.registration(user).test().assertError(expected)
    }

    @Test
    fun `on registration EXPECT BadRequestException`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val exception = HttpException(Response.error<Any>(400,  "BAD REQUEST".toResponseBody("text/plain".toMediaTypeOrNull())))
        val expected = BadRequestException::class.java

        Mockito.`when`(authDataSource.registration(userModel)).thenReturn(Single.error(exception))

        authRepositoryImpl.registration(user).test().assertError(expected)
    }

    @Test
    fun `on registration EXPECT NotFoundException`() {
        val authRepositoryImpl = AuthRepositoryImpl(authDataSource)
        val exception = HttpException(Response.error<Any>(404,  "NOT FOUND".toResponseBody("text/plain".toMediaTypeOrNull())))
        val expected = NotFoundException::class.java

        Mockito.`when`(authDataSource.registration(userModel)).thenReturn(Single.error(exception))

        authRepositoryImpl.registration(user).test().assertError(expected)
    }
}