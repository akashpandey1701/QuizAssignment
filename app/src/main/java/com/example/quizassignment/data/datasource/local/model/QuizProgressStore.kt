package com.example.quizassignment.data.datasource.local.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizProgressStore(
    val progressBySubjectId: Map<String, PersistedQuizProgress> = emptyMap()
)

@Serializable
data class PersistedQuizProgress(
    val subject: PersistedSubject,
    val session: PersistedQuizSession,
    val status: PersistedQuizStatus = PersistedQuizStatus.InProgress
)

@Serializable
enum class PersistedQuizStatus {
    @SerialName("IN_PROGRESS")
    InProgress,
    @SerialName("COMPLETED")
    Completed
}

@Serializable
data class PersistedSubject(
    val id: String,
    val title: String,
    val description: String,
    val questionsUrl: String
)

@Serializable
data class PersistedQuizSession(
    val questions: List<PersistedQuestion>,
    val currentQuestionIndex: Int,
    val records: List<PersistedQuestionRecord>,
    val currentStreak: Int,
    val longestStreak: Int
)

@Serializable
data class PersistedQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)

@Serializable
data class PersistedQuestionRecord(
    val questionId: Int,
    val selectedOptionIndex: Int?,
    val wasSkipped: Boolean,
    val wasCorrect: Boolean
)
