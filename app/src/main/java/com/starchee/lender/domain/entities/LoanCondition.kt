package com.starchee.lender.domain.entities

data class LoanCondition(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)