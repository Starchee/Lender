package com.starchee.lender.data.dataSource.auth

import com.starchee.lender.data.models.LocalUserModel
import com.starchee.lender.data.models.UserModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    fun login(@Body user: UserModel): Single<String>

    @POST("registration")
    fun registration(@Body user: UserModel): Single<LocalUserModel>
}