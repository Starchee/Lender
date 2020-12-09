package com.starchee.lender.data.repositories

import com.starchee.lender.data.dataSource.auth.AuthDataSource
import com.starchee.lender.data.models.LocalUserModel
import com.starchee.lender.data.models.UserModel
import com.starchee.lender.domain.entities.*
import com.starchee.lender.domain.repositories.AuthRepository
import io.reactivex.Single
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    AuthRepository {

    override fun login(user: User): Single<String> =
        authDataSource.login(user.mapToUserModel()).mapNetworkErrors()

    override fun registration(user: User): Single<LocalUser> =
        authDataSource.registration(user.mapToUserModel()).map { it.mapToLocalUser() }
            .mapNetworkErrors()

    private fun User.mapToUserModel() = UserModel(this.name, this.password)

    private fun LocalUserModel.mapToLocalUser() = LocalUser(this.name, this.role)

    private inline fun <reified T> Single<T>.mapNetworkErrors(): Single<T> =
        this.onErrorResumeNext { error ->
            when (error) {
                is UnknownHostException -> Single.error(NoNetWorkException())
                is HttpException -> mapHttpError(error)
                else -> Single.error(error)
            }
        }

    private fun <T> mapHttpError(error: HttpException): Single<T> =
        when (error.code()) {
            400 -> Single.error(BadRequestException())
            404 -> Single.error(NotFoundException())
            else -> Single.error(error)
        }
}