package com.example.quizassignment.core.common

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    data class Dynamic(val value: String) : UiText
    data class Resource(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText
}

fun UiText.asString(context: Context): String = when (this) {
    is UiText.Dynamic -> value
    is UiText.Resource -> context.getString(resId, *args.toTypedArray())
}
