package com.example.quizassignment.feature.payment.platform

import android.app.Activity
import android.content.Context
import com.example.quizassignment.BuildConfig
import com.example.quizassignment.feature.payment.domain.PaymentRequest
import com.razorpay.Checkout
import org.json.JSONObject
import javax.inject.Inject

class RazorpayCheckout @Inject constructor() {

    fun preload(context: Context) {
        Checkout.preload(context.applicationContext)
    }

    fun open(
        activity: Activity,
        request: PaymentRequest
    ) {
        val options = JSONObject().apply {
            put("name", request.name)
            put("description", request.description)
            put("currency", request.currency)
            put("amount", request.amount)

            put(
                "theme",
                JSONObject().apply {
                    put("color", "#0D94FB")
                }
            )

            put(
                "retry",
                JSONObject().apply {
                    put("enabled", true)
                    put("max_count", 4)
                }
            )
        }

        Checkout().apply {
            setKeyID(BuildConfig.RAZORPAY_KEY_ID)
            open(activity, options)
        }
    }
}