package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.QuizResult
import com.example.quizassignment.domain.model.QuizSession
import javax.inject.Inject

class BuildQuizResultUseCase @Inject constructor() {
    operator fun invoke(session: QuizSession): AppResult<QuizResult> {
        if (!session.isCompleted) {
            return AppResult.Error(message = "Quiz results are unavailable before completion.")
        }

        val correctAnswers = session.records.count { it.wasCorrect }
        val skippedQuestions = session.records.count { it.wasSkipped }

        return AppResult.Success(
            QuizResult(
                correctAnswers = correctAnswers,
                totalQuestions = session.questions.size,
                skippedQuestions = skippedQuestions,
                longestStreak = session.longestStreak
            )
        )
    }
}
