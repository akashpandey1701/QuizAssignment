package com.example.quizassignment.domain.repository

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.Subject

interface QuizRepository {
    suspend fun getSubjects(): AppResult<List<Subject>>
    suspend fun getQuestions(questionsUrl: String): AppResult<List<Question>>
    suspend fun getProgress(subjectId: String): AppResult<QuizProgress?>
    suspend fun saveProgress(progress: QuizProgress): AppResult<Unit>
}
