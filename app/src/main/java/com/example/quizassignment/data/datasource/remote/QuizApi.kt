package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import retrofit2.http.GET

interface QuizApi {
    @GET("dr-samrat/53846277a8fcb034e482906ccc0d12b2/raw")
    suspend fun fetchQuestions(): List<QuestionDto>
}
