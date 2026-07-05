package com.example.quizassignment.domain.model

data class QuizSession(
    val questions: List<Question>,
    val currentQuestionIndex: Int,
    val records: List<QuestionRecord>,
    val currentStreak: Int,
    val longestStreak: Int
) {
    val isCompleted: Boolean
        get() = currentQuestionIndex >= questions.size

    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val currentRecord: QuestionRecord?
        get() = records.getOrNull(currentQuestionIndex)
}
