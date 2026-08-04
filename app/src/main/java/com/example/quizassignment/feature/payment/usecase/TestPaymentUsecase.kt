package com.example.quizassignment.feature.payment.usecase

import com.example.quizassignment.feature.payment.domain.PaymentRequest
import javax.inject.Inject

class TestPaymentUsecase @Inject constructor() {

    operator fun invoke(): PaymentRequest {
        return PaymentRequest(
            amount = 10_000L,
            currency = "INR",
            name = "MCQ Quiz",
            description = "Quiz test payment"
        )
    }
}