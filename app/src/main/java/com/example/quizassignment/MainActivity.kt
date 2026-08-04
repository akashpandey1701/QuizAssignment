package com.example.quizassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.quizassignment.core.designsystem.theme.QuizAssignmentTheme
import com.example.quizassignment.feature.payment.platform.RazorpayCheckout
import com.example.quizassignment.feature.payment.presentation.PaymentEffect
import com.example.quizassignment.feature.payment.presentation.PaymentViewModel
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {

    private val paymentViewModel: PaymentViewModel by viewModels()

    @Inject
    lateinit var checkoutLauncher: RazorpayCheckout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkoutLauncher.preload(applicationContext)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                paymentViewModel.effects.collect { effect ->
                    when (effect) {
                        is PaymentEffect.OpenCheckout -> {
                            runCatching {
                                checkoutLauncher.open(
                                    activity = this@MainActivity,
                                    request = effect.request
                                )
                            }.onSuccess {
                                paymentViewModel.onCheckoutOpened()
                            }.onFailure {
                                paymentViewModel.onPaymentError(
                                    it.message ?: "Unable to open payment"
                                )
                            }
                        }
                    }
                }
            }
        }

        setContent {
            QuizAssignmentTheme {
                QuizApp(
                    onPaymentClick = paymentViewModel::onPaymentClick
                )
            }
        }
    }

    override fun onPaymentSuccess(paymentId: String?) {
        if (paymentId.isNullOrBlank()) {
            paymentViewModel.onPaymentError(
                "Payment completed without a payment ID"
            )
        } else {
            paymentViewModel.onPaymentSuccess(paymentId)
        }
    }

    override fun onPaymentError(
        errorCode: Int,
        response: String?
    ) {
        paymentViewModel.onPaymentError(
            response ?: "Payment cancelled or failed"
        )
    }
}
