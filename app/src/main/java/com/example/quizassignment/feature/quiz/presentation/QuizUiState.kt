package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.R
import com.example.quizassignment.core.common.UiText

data class QuizUiState(
    val currentQuestion: QuestionUiModel = QuestionUiModel.Empty,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val selectedOptionIndex: Int? = null,
    val correctOptionIndex: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val isStreakHighlighted: Boolean = false,
    val canAnswer: Boolean = false,
    val canSkip: Boolean = false,
    val isSessionReady: Boolean = false,
    val supportingText: UiText = UiText.Resource(R.string.quiz_loading_session),
    val errorMessage: UiText? = null,
    val shouldNavigateToResults: Boolean = false
)
