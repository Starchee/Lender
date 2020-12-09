package com.starchee.lender.domain.entities

data class LoanApplication(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val phone: String
)
