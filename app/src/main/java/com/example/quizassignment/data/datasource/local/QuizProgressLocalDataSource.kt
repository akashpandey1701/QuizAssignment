package com.example.quizassignment.data.datasource.local

import com.example.quizassignment.domain.model.QuizProgress

interface QuizProgressLocalDataSource {
    suspend fun getProgress(subjectId: String): QuizProgress?
    suspend fun saveProgress(progress: QuizProgress)
}
