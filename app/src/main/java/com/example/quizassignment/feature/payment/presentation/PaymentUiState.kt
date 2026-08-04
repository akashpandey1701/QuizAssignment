package com.example.quizassignment.feature.payment.presentation

sealed interface PaymentUiState {
    data object Idle : PaymentUiState
    data object OpeningCheckout : PaymentUiState
    data class Success(val paymentId: String) : PaymentUiState
    data class Error(val message: String) : PaymentUiState
}