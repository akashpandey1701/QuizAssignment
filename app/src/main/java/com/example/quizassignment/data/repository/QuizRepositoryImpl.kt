package com.example.quizassignment.data.repository

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.data.datasource.local.QuizProgressLocalDataSource
import com.example.quizassignment.data.datasource.remote.QuizRemoteDataSource
import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.data.mapper.QuestionMapper
import com.example.quizassignment.data.mapper.SubjectMapper
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.Subject
import com.example.quizassignment.domain.repository.QuizRepository
import javax.inject.Inject
import kotlinx.coroutines.CancellationException

class QuizRepositoryImpl @Inject constructor(
    private val remoteDataSource: QuizRemoteDataSource,
    private val localDataSource: QuizProgressLocalDataSource,
    private val questionMapper: QuestionMapper,
    private val subjectMapper: SubjectMapper
) : QuizRepository {

    override suspend fun getSubjects(): AppResult<List<Subject>> {
        return try {
            val subjects = remoteDataSource.fetchSubjects()
                .map(subjectMapper::map)
                .filter { subject ->
                    subject.id.isNotBlank() &&
                        subject.title.isNotBlank() &&
                        subject.questionsUrl.isNotBlank()
                }
            if (subjects.isEmpty()) {
                AppResult.Error(message = "Subject list is empty.")
            } else {
                AppResult.Success(subjects)
            }
        } catch (error: Exception) {
            error.asRepositoryError("Unable to load subjects.")
        }
    }

    override suspend fun getQuestions(questionsUrl: String): AppResult<List<Question>> {
        if (questionsUrl.isBlank()) {
            return AppResult.Error(message = "Quiz questions URL is unavailable.")
        }
        return try {
            mapQuestions(remoteDataSource.fetchQuestions(questionsUrl))
        } catch (error: Exception) {
            error.asRepositoryError("Unable to load quiz questions.")
        }
    }

    override suspend fun getProgress(subjectId: String): AppResult<QuizProgress?> {
        if (subjectId.isBlank()) {
            return AppResult.Error(message = "Subject identifier is unavailable.")
        }
        return try {
            AppResult.Success(localDataSource.getProgress(subjectId))
        } catch (error: Exception) {
            error.asRepositoryError("Unable to restore quiz progress.")
        }
    }

    override suspend fun saveProgress(progress: QuizProgress): AppResult<Unit> {
        return try {
            localDataSource.saveProgress(progress)
            AppResult.Success(Unit)
        } catch (error: Exception) {
            error.asRepositoryError("Unable to save quiz progress.")
        }
    }

    private fun mapQuestions(questions: List<QuestionDto>): AppResult<List<Question>> {
        if (questions.isEmpty()) {
            return AppResult.Error(message = "Quiz question list is empty.")
        }

        return AppResult.Success(questions.map(questionMapper::map))
    }

    private fun <T> Exception.asRepositoryError(message: String): AppResult<T> {
        if (this is CancellationException) throw this
        return AppResult.Error(message = message, cause = this)
    }
}
