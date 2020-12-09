package com.starchee.lender.data.models

import com.google.gson.annotations.SerializedName

data class LocalUserModel(
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String
)