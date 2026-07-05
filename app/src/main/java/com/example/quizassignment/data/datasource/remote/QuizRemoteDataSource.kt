package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.QuestionDto

interface QuizRemoteDataSource {
    suspend fun fetchQuestions(): List<QuestionDto>
}
