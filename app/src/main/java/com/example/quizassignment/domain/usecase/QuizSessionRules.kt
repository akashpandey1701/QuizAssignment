package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuizSession

private const val RequiredOptionsCount = 4

internal fun validateQuestions(questions: List<Question>): AppResult<List<Question>> {
    if (questions.isEmpty()) {
        return AppResult.Error(message = "Quiz question list is empty.")
    }

    val invalidQuestion = questions.firstOrNull { question ->
        question.options.size != RequiredOptionsCount ||
            question.correctOptionIndex !in question.options.indices
    }

    return if (invalidQuestion == null) {
        AppResult.Success(questions)
    } else {
        AppResult.Error(
            message = "Question ${invalidQuestion.id} has invalid options or answer index."
        )
    }
}

internal fun validateActiveSession(session: QuizSession): AppResult<QuizSession> {
    return if (session.isCompleted) {
        AppResult.Error(message = "Quiz session has already completed.")
    } else {
        AppResult.Success(session)
    }
}
