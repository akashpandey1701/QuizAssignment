package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.QuestionRecord
import com.example.quizassignment.domain.model.QuizSession
import javax.inject.Inject

class SelectAnswerUseCase @Inject constructor() {
    operator fun invoke(
        session: QuizSession,
        optionIndex: Int
    ): AppResult<QuizSession> {
        when (val validation = validateActiveSession(session)) {
            is AppResult.Error -> return validation
            is AppResult.Success -> Unit
        }

        val question = session.currentQuestion
            ?: return AppResult.Error(message = "Current question is unavailable.")

        if (session.currentRecord != null) {
            return AppResult.Error(message = "Current question has already been answered.")
        }

        if (optionIndex !in question.options.indices) {
            return AppResult.Error(message = "Selected answer index is invalid.")
        }

        val wasCorrect = optionIndex == question.correctOptionIndex
        val nextStreak = if (wasCorrect) session.currentStreak + 1 else 0

        return AppResult.Success(
            session.copy(
                records = session.records + QuestionRecord(
                    questionId = question.id,
                    selectedOptionIndex = optionIndex,
                    wasSkipped = false,
                    wasCorrect = wasCorrect
                ),
                currentStreak = nextStreak,
                longestStreak = maxOf(session.longestStreak, nextStreak)
            )
        )
    }
}
