package com.example.quizassignment.domain.model

data class QuestionRecord(
    val questionId: Int,
    val selectedOptionIndex: Int?,
    val wasSkipped: Boolean,
    val wasCorrect: Boolean
)
