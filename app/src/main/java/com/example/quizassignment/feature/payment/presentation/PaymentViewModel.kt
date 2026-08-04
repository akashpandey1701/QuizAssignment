package com.example.quizassignment.feature.payment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizassignment.feature.payment.usecase.TestPaymentUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val prepareTestPayment: TestPaymentUsecase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<PaymentEffect>()
    val effects = _effects.receiveAsFlow()

    fun onPaymentClick() {
        if (_uiState.value == PaymentUiState.OpeningCheckout) return

        val request = prepareTestPayment()
        _uiState.value = PaymentUiState.OpeningCheckout

        viewModelScope.launch {
            _effects.send(PaymentEffect.OpenCheckout(request))
        }
    }

    fun onPaymentSuccess(paymentId: String) {
        _uiState.value = PaymentUiState.Success(paymentId)
    }

    fun onPaymentError(message: String) {
        _uiState.value = PaymentUiState.Error(message)
    }

    fun onCheckoutOpened() {
        _uiState.value = PaymentUiState.Idle
    }

    fun consumeResult() {
        _uiState.value = PaymentUiState.Idle
    }
}