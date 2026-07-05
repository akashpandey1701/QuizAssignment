package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import javax.inject.Inject

class RetrofitQuizRemoteDataSource @Inject constructor(
    private val quizApi: QuizApi
) : QuizRemoteDataSource {

    override suspend fun fetchQuestions(): List<QuestionDto> = quizApi.fetchQuestions()
}
