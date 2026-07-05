package com.example.quizassignment.data.repository

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.data.datasource.remote.QuizRemoteDataSource
import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.data.mapper.QuestionMapper
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.repository.QuizRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

class QuizRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuizRemoteDataSource,
    private val questionMapper: QuestionMapper
) : QuizRepository {

    override suspend fun getQuestions(): AppResult<List<Question>> {
        return try {
            mapQuestions(remoteDataSource.fetchQuestions())
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            AppResult.Error(
                message = "Unable to load quiz questions.",
                cause = error
            )
        }
    }

    private fun mapQuestions(questions: List<QuestionDto>): AppResult<List<Question>> {
        if (questions.isEmpty()) {
            return AppResult.Error(message = "Quiz question list is empty.")
        }

        return AppResult.Success(questions.map(questionMapper::map))
    }
}
