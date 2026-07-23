package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.data.datasource.remote.dto.SubjectDto

interface QuizRemoteDataSource {
    suspend fun fetchSubjects(): List<SubjectDto>
    suspend fun fetchQuestions(questionsUrl: String): List<QuestionDto>
}
