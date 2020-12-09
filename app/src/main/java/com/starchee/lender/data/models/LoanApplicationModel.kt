package com.starchee.lender.data.models

import com.google.gson.annotations.SerializedName

data class LoanApplicationModel(
    @SerializedName("amount") val amount: Int,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int,
    @SerializedName("phoneNumber") val phone: String
)