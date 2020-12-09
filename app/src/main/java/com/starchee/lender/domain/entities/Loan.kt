package com.starchee.lender.domain.entities

data class Loan(
    val amount: Int,
    val date: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phone: String,
    val state: LoanState
)