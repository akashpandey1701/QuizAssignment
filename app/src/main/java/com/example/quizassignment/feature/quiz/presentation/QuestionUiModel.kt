package com.example.quizassignment.feature.quiz.presentation

data class QuestionUiModel(
    val id: Int,
    val questionNumber: Int,
    val totalQuestions: Int,
    val title: String,
    val options: List<String>
) {
    companion object {
        val Empty = QuestionUiModel(
            id = 0,
            questionNumber = 1,
            totalQuestions = 1,
            title = "",
            options = listOf("", "", "", "")
        )
    }
}
