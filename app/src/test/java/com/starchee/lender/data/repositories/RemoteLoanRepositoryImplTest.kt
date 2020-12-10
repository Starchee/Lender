package com.starchee.lender.data.repositories

import com.nhaarman.mockito_kotlin.mock
import com.starchee.lender.data.dataSource.loan.RemoteLoanDataSource
import com.starchee.lender.data.models.LoanApplicationModel
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import com.starchee.lender.data.utils.DateUtils
import com.starchee.lender.domain.entities.*
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class RemoteLoanRepositoryImplTest {

    private val remoteLoanDataSource: RemoteLoanDataSource = mock()
    private val dateUtils: DateUtils = DateUtils()
    private val token = "token"

    @Test
    fun `on getLoanCondition EXPECTED LoanCondition`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanConditionModel = LoanConditionModel(maxAmount = 20000, period = 20, percent = 14.5)
        val expected = LoanCondition(maxAmount = 20000, period = 20, percent = 14.5)

        Mockito.`when`(remoteLoanDataSource.getLoanCondition(token))
            .thenReturn(Single.just(loanConditionModel))

        remoteLoanRepository.getLoanCondition(token).test().assertValue(expected)
    }

    @Test
    fun `on getLoanCondition EXPECTED Throwable`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val expected = Throwable()

        Mockito.`when`(remoteLoanDataSource.getLoanCondition(token))
            .thenReturn(Single.error(expected))

        remoteLoanRepository.getLoanCondition(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanCondition EXPECTED NoNetWorkException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = UnknownHostException()
        val expected = NoNetWorkException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanCondition(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanCondition EXPECTED BadRequestException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = HttpException(
            Response.error<Any>(
                400,
                "BAD REQUEST".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = BadRequestException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanCondition(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanCondition EXPECTED NotFoundException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = HttpException(
            Response.error<Any>(
                404,
                "NOT FOUND".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = NotFoundException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanCondition(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanCondition EXPECTED ForbiddenException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = HttpException(
            Response.error<Any>(
                403,
                "Forbidden".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = ForbiddenException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanCondition(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanCondition(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanList EXPECT List of Loan`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanModelList = listOf(
            LoanModel(
                amount = 20000,
                date = "2020-12-06T17:07:32.431+00:00",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = "APPROVED"
            ),
            LoanModel(
                amount = 20000,
                date = "2020-12-06T17:07:32.431+00:00",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REGISTERED"
            ),
            LoanModel(
                amount = 20000,
                date = "2020-12-06T17:07:32.431+00:00",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = "REJECTED"
            )
        )

        val expected = listOf(
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 100,
                lastName = "Pupkin",
                percent = 14.5,
                period = 20,
                phone = "88002000600",
                state = LoanState.APPROVED
            ),
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Petya",
                id = 200,
                lastName = "Tytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REGISTERED
            ),
            Loan(
                amount = 20000,
                date = "07.12.2020 00:07",
                firstName = "Vasya",
                id = 300,
                lastName = "Ytkin",
                percent = 12.5,
                period = 40,
                phone = "88002000600",
                state = LoanState.REJECTED
            )
        )

        Mockito.`when`(remoteLoanDataSource.getLoanList(token))
            .thenReturn(Single.just(loanModelList))

        remoteLoanRepository.getLoanList(token).test().assertValue(expected)
    }

    @Test
    fun `on getLoanList EXPECTED Throwable`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val expected = Throwable()

        Mockito.`when`(remoteLoanDataSource.getLoanList(token))
            .thenReturn(Single.error(expected))

        remoteLoanRepository.getLoanList(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanList EXPECTED NoNetWorkException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = UnknownHostException()
        val expected = NoNetWorkException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanList(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanList(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanList EXPECTED BadRequestException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = HttpException(
            Response.error<Any>(
                400,
                "BAD REQUEST".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = BadRequestException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanList(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanList(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanList EXPECTED NotFoundException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = HttpException(
            Response.error<Any>(
                404,
                "NOT FOUND".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = NotFoundException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanList(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanList(token).test().assertError(expected)
    }

    @Test
    fun `on getLoanList EXPECTED ForbiddenException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val exception = HttpException(
            Response.error<Any>(
                403,
                "Forbidden".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = ForbiddenException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanList(token))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanList(token).test().assertError(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECT Loan`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )
        val loanModel = LoanModel(
            amount = 20000,
            date = "2020-12-06T17:07:32.431+00:00",
            firstName = "Vasya",
            id = 100,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = "APPROVED"
        )
        val expected = Loan(
            amount = 20000,
            date = "07.12.2020 00:07",
            firstName = "Vasya",
            id = 100,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = LoanState.APPROVED
        )

        Mockito.`when`(remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.just(loanModel))

        remoteLoanRepository.makeLoanApplication(token, loanApplication, loanCondition)
            .test()
            .assertValue(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECTED Throwable`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )

        val expected = Throwable()

        Mockito.`when`(remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.error(expected))

        remoteLoanRepository.makeLoanApplication(token, loanApplication, loanCondition).test()
            .assertError(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECTED NoNetWorkException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )
        val exception = UnknownHostException()
        val expected = NoNetWorkException::class.java

        Mockito.`when`(remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.makeLoanApplication(token, loanApplication, loanCondition).test()
            .assertError(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECTED BadRequestException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )
        val exception = HttpException(
            Response.error<Any>(
                400,
                "BAD REQUEST".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = BadRequestException::class.java

        Mockito.`when`(remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.makeLoanApplication(token, loanApplication, loanCondition).test()
            .assertError(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECTED NotFoundException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )
        val exception = HttpException(
            Response.error<Any>(
                404,
                "NOT FOUND".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = NotFoundException::class.java

        Mockito.`when`(remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.makeLoanApplication(token, loanApplication, loanCondition).test()
            .assertError(expected)
    }

    @Test
    fun `on makeLoanApplication EXPECTED ForbiddenException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val loanApplicationModel = LoanApplicationModel(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600"
        )
        val loanApplication = LoanApplication(
            amount = 20000,
            firstName = "Vasya",
            lastName = "Pupkin",
            phone = "88002000600"
        )
        val loanCondition = LoanCondition(
            maxAmount = 20000,
            percent = 14.5,
            period = 20,
        )
        val exception = HttpException(
            Response.error<Any>(
                403,
                "Forbidden".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = ForbiddenException::class.java

        Mockito.`when`(remoteLoanDataSource.makeLoanApplication(token, loanApplicationModel))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.makeLoanApplication(token, loanApplication, loanCondition).test()
            .assertError(expected)
    }

    @Test
    fun `on getLoanById EXPECT Loan`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val id = 100
        val loanModel = LoanModel(
            amount = 20000,
            date = "2020-12-06T17:07:32.431+00:00",
            firstName = "Vasya",
            id = id,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = "APPROVED"
        )
        val expected = Loan(
            amount = 20000,
            date = "07.12.2020 00:07",
            firstName = "Vasya",
            id = id,
            lastName = "Pupkin",
            percent = 14.5,
            period = 20,
            phone = "88002000600",
            state = LoanState.APPROVED
        )

        Mockito.`when`(remoteLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.just(loanModel))

        remoteLoanRepository.getLoanById(token, id).test().assertValue(expected)
    }

    @Test
    fun `on getLoanById EXPECTED Throwable`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val id = 100
        val expected = Throwable()

        Mockito.`when`(remoteLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error(expected))

        remoteLoanRepository.getLoanById(token, id).test().assertError(expected)
    }

    @Test
    fun `on getLoanById EXPECTED NoNetWorkException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val id = 100
        val exception = UnknownHostException()
        val expected = NoNetWorkException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanById(token, id).test().assertError(expected)
    }

    @Test
    fun `on getLoanById EXPECTED BadRequestException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val id = 100
        val exception = HttpException(
            Response.error<Any>(
                400,
                "BAD REQUEST".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = BadRequestException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanById(token, id).test().assertError(expected)
    }

    @Test
    fun `on getLoanById EXPECTED NotFoundException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val id = 100
        val exception = HttpException(
            Response.error<Any>(
                404,
                "NOT FOUND".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = NotFoundException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanById(token, id).test().assertError(expected)
    }

    @Test
    fun `on getLoanById EXPECTED ForbiddenException`() {
        val remoteLoanRepository =
            RemoteLoanRepositoryImpl(remoteLoanDataSource, dateUtils)
        val id = 100
        val exception = HttpException(
            Response.error<Any>(
                403,
                "Forbidden".toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        val expected = ForbiddenException::class.java

        Mockito.`when`(remoteLoanDataSource.getLoanById(token, id))
            .thenReturn(Single.error(exception))

        remoteLoanRepository.getLoanById(token, id).test().assertError(expected)
    }
}