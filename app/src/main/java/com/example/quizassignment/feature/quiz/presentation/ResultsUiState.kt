package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.R
import com.example.quizassignment.core.common.UiText

enum class QuestionResultStatus {
    Correct,
    Incorrect,
    Skipped
}

data class QuestionResultUiModel(
    val questionNumber: Int,
    val status: QuestionResultStatus,
    val completesStreakMilestone: Boolean
)

data class ResultsUiState(
    val correctAnswers: Int = 0,
    val totalQuestions: Int = 0,
    val skippedQuestions: Int = 0,
    val longestStreak: Int = 0,
    val statusLabel: UiText = UiText.Resource(R.string.results_status_complete),
    val headline: UiText = UiText.Resource(R.string.results_headline_great_work),
    val supportingText: UiText = UiText.Resource(R.string.results_support_default),
    val questionResults: List<QuestionResultUiModel> = emptyList()
) {
    val accuracyPercent: Int
        get() = if (totalQuestions == 0) 0 else ((correctAnswers.toFloat() / totalQuestions) * 100).toInt()
}
