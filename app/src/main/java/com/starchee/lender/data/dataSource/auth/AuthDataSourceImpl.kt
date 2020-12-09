package com.starchee.lender.data.dataSource.auth

import com.starchee.lender.data.models.LocalUserModel
import com.starchee.lender.data.models.UserModel
import io.reactivex.Single
import javax.inject.Inject

interface AuthDataSource {
    fun login(userModel: UserModel): Single<String>
    fun registration(userModel: UserModel): Single<LocalUserModel>
}

class AuthDataSourceImpl @Inject constructor(private val authApi: AuthApi): AuthDataSource {

    override fun login(userModel: UserModel): Single<String> = authApi.login(userModel)

    override fun registration(userModel: UserModel): Single<LocalUserModel> =
        authApi.registration(userModel)

}