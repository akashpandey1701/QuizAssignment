package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.SubjectDto
import kotlinx.serialization.json.JsonArray
import retrofit2.http.GET
import retrofit2.http.Url

interface QuizApi {
    @GET("dr-samrat/ee986f16da9d8303c1acfd364ece22c5/raw")
    suspend fun fetchSubjects(): List<SubjectDto>

    @GET
    suspend fun fetchQuestions(@Url questionsUrl: String): JsonArray
}
