package com.example.quizassignment.domain.model

data class QuizProgress(
    val subject: Subject,
    val session: QuizSession
) {
    val isCompleted: Boolean
        get() = session.isCompleted
}
