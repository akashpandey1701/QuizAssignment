package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.QuizSession
import javax.inject.Inject

class AdvanceToNextQuestionUseCase @Inject constructor() {
    operator fun invoke(session: QuizSession): AppResult<QuizSession> {
        when (val validation = validateActiveSession(session)) {
            is AppResult.Error -> return validation
            is AppResult.Success -> Unit
        }

        if (session.currentRecord == null) {
            return AppResult.Error(message = "Resolve the current question before advancing.")
        }

        return AppResult.Success(
            session.copy(
                currentQuestionIndex = session.currentQuestionIndex + 1
            )
        )
    }
}
