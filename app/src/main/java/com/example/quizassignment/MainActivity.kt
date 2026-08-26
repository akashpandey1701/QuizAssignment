package com.example.quizassignment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.quizassignment.core.designsystem.theme.QuizAssignmentTheme
import com.example.quizassignment.feature.payment.platform.RazorpayCheckout
import com.example.quizassignment.feature.payment.presentation.PaymentEffect
import com.example.quizassignment.feature.payment.presentation.PaymentViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {

    private val paymentViewModel: PaymentViewModel by viewModels()

    @Inject
    lateinit var checkoutLauncher: RazorpayCheckout

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

            } else {
                // User denied notification permission.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkoutLauncher.preload(applicationContext)
        requestNotificationPermission()
        fetchFirebaseToken()
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

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        val permissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {
            notificationPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    private fun fetchFirebaseToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->

                if (!task.isSuccessful) {
                    Log.e(
                        "FCM_TOKEN",
                        "Token generation failed",
                        task.exception
                    )
                    return@addOnCompleteListener
                }

                val token = task.result

                Log.d("FCM_TOKEN", "Token: $token")
            }
    }
}
