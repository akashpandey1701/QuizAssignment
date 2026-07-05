package com.example.quizassignment.domain.repository

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(): AppResult<List<Question>>
}
