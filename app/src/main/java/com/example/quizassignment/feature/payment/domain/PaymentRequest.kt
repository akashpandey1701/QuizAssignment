package com.example.quizassignment.feature.payment.domain

data class PaymentRequest(
    val amount: Long,
    val description: String,
    val currency: String,
    val name: String
)