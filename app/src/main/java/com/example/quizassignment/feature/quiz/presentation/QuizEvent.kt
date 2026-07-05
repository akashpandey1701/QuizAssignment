package com.example.quizassignment.feature.quiz.presentation

sealed interface QuizEvent {
    data class AnswerSelected(val optionIndex: Int) : QuizEvent
    data object SkipClicked : QuizEvent
}
