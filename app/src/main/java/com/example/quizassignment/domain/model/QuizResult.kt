package com.example.quizassignment.domain.model

data class QuizResult(
    val correctAnswers: Int,
    val totalQuestions: Int,
    val skippedQuestions: Int,
    val longestStreak: Int
)
