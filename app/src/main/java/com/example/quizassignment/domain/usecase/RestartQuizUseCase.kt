package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.QuizSession
import com.example.quizassignment.domain.usecase.StartQuizSessionUseCase
import javax.inject.Inject

class RestartQuizUseCase @Inject constructor(
    private val startQuizSessionUseCase: StartQuizSessionUseCase
) {
    operator fun invoke(session: QuizSession?): AppResult<QuizSession> {
        val currentSession = session ?: return AppResult.Error(
            message = "Cannot restart a quiz session that does not exist."
        )
        return startQuizSessionUseCase(currentSession.questions)
    }
}
