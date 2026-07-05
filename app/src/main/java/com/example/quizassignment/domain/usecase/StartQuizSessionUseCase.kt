package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuizSession
import javax.inject.Inject

class StartQuizSessionUseCase @Inject constructor() {
    operator fun invoke(questions: List<Question>): AppResult<QuizSession> {
        return when (val validation = validateQuestions(questions)) {
            is AppResult.Error -> validation
            is AppResult.Success -> AppResult.Success(
                QuizSession(
                    questions = validation.data,
                    currentQuestionIndex = 0,
                    records = emptyList(),
                    currentStreak = 0,
                    longestStreak = 0
                )
            )
        }
    }
}
