package com.example.quizassignment.feature.payment.presentation

import com.example.quizassignment.feature.payment.domain.PaymentRequest

sealed interface PaymentEffect {
    data class OpenCheckout(
        val request: PaymentRequest
    ) : PaymentEffect
}