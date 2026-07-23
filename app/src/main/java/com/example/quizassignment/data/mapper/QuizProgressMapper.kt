package com.example.quizassignment.data.mapper

import com.example.quizassignment.data.datasource.local.model.PersistedQuestion
import com.example.quizassignment.data.datasource.local.model.PersistedQuestionRecord
import com.example.quizassignment.data.datasource.local.model.PersistedQuizProgress
import com.example.quizassignment.data.datasource.local.model.PersistedQuizSession
import com.example.quizassignment.data.datasource.local.model.PersistedQuizStatus
import com.example.quizassignment.data.datasource.local.model.PersistedSubject
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuestionRecord
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.QuizSession
import com.example.quizassignment.domain.model.Subject
import javax.inject.Inject

class QuizProgressMapper @Inject constructor() {

    fun toPersisted(progress: QuizProgress): PersistedQuizProgress = PersistedQuizProgress(
        subject = progress.subject.toPersisted(),
        session = progress.session.toPersisted(),
        status = if (progress.isCompleted) {
            PersistedQuizStatus.Completed
        } else {
            PersistedQuizStatus.InProgress
        }
    )

    fun toDomain(progress: PersistedQuizProgress): QuizProgress = QuizProgress(
        subject = progress.subject.toDomain(),
        session = progress.session.toDomain()
    )

    private fun Subject.toPersisted() = PersistedSubject(
        id = id,
        title = title,
        description = description,
        questionsUrl = questionsUrl
    )

    private fun PersistedSubject.toDomain() = Subject(
        id = id,
        title = title,
        description = description,
        questionsUrl = questionsUrl
    )

    private fun QuizSession.toPersisted() = PersistedQuizSession(
        questions = questions.map { it.toPersisted() },
        currentQuestionIndex = currentQuestionIndex,
        records = records.map { it.toPersisted() },
        currentStreak = currentStreak,
        longestStreak = longestStreak
    )

    private fun PersistedQuizSession.toDomain() = QuizSession(
        questions = questions.map { it.toDomain() },
        currentQuestionIndex = currentQuestionIndex,
        records = records.map { it.toDomain() },
        currentStreak = currentStreak,
        longestStreak = longestStreak
    )

    private fun Question.toPersisted() = PersistedQuestion(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex
    )

    private fun PersistedQuestion.toDomain() = Question(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex
    )

    private fun QuestionRecord.toPersisted() = PersistedQuestionRecord(
        questionId = questionId,
        selectedOptionIndex = selectedOptionIndex,
        wasSkipped = wasSkipped,
        wasCorrect = wasCorrect
    )

    private fun PersistedQuestionRecord.toDomain() = QuestionRecord(
        questionId = questionId,
        selectedOptionIndex = selectedOptionIndex,
        wasSkipped = wasSkipped,
        wasCorrect = wasCorrect
    )
}
