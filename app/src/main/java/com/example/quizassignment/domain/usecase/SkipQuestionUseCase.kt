package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.QuestionRecord
import com.example.quizassignment.domain.model.QuizSession
import javax.inject.Inject

class SkipQuestionUseCase @Inject constructor() {
    operator fun invoke(session: QuizSession): AppResult<QuizSession> {
        when (val validation = validateActiveSession(session)) {
            is AppResult.Error -> return validation
            is AppResult.Success -> Unit
        }

        val question = session.currentQuestion
            ?: return AppResult.Error(message = "Current question is unavailable.")

        if (session.currentRecord != null) {
            return AppResult.Error(message = "Current question has already been resolved.")
        }

        return AppResult.Success(
            session.copy(
                records = session.records + QuestionRecord(
                    questionId = question.id,
                    selectedOptionIndex = null,
                    wasSkipped = true,
                    wasCorrect = false
                ),
                currentStreak = 0
            )
        )
    }
}
