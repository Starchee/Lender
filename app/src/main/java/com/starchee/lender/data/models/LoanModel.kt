package com.starchee.lender.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "loans")
data class LoanModel(
    @SerializedName("amount") val amount: Int,
    @SerializedName("date") val date: String,
    @SerializedName("firstName") val firstName: String,
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int,
    @SerializedName("phoneNumber") val phone: String,
    @SerializedName("state") val state: String
)