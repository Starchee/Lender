package com.starchee.lender.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "conditions")
data class LoanConditionModel(
    @PrimaryKey @Expose(serialize = false, deserialize = false) val id: Int = 0,
    @SerializedName("maxAmount") val maxAmount: Int,
    @SerializedName("percent") val percent: Double,
    @SerializedName("period") val period: Int
)