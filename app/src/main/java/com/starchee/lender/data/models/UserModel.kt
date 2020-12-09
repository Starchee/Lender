package com.starchee.lender.data.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String,
)