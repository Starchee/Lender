package com.starchee.lender.data.dataSource.loan

import com.starchee.lender.data.models.LoanApplicationModel
import com.starchee.lender.data.models.LoanConditionModel
import com.starchee.lender.data.models.LoanModel
import io.reactivex.Single
import retrofit2.http.*

interface LoanApi {

    @GET("loans/conditions")
    fun getLoanCondition(@Header("Authorization") token: String): Single<LoanConditionModel>

    @GET("loans/all")
    fun getLoanList(@Header("Authorization") token: String): Single<List<LoanModel>>

    @GET("loans/{id}")
    fun getLoanById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<LoanModel>

    @POST("loans/")
    fun makeLoanApplication(
        @Header("Authorization") token: String,
        @Body loanApplicationModel: LoanApplicationModel
    ): Single<LoanModel>
}