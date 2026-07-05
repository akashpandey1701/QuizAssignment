package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.Question
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizUseCasesTest {

    private val questions = listOf(
        Question(
            id = 1,
            question = "First question",
            options = listOf("A", "B", "C", "D"),
            correctOptionIndex = 1
        ),
        Question(
            id = 2,
            question = "Second question",
            options = listOf("A", "B", "C", "D"),
            correctOptionIndex = 2
        )
    )

    private val startQuizSessionUseCase = StartQuizSessionUseCase()
    private val selectAnswerUseCase = SelectAnswerUseCase()
    private val skipQuestionUseCase = SkipQuestionUseCase()
    private val advanceToNextQuestionUseCase = AdvanceToNextQuestionUseCase()
    private val buildQuizResultUseCase = BuildQuizResultUseCase()
    private val restartQuizUseCase = RestartQuizUseCase(startQuizSessionUseCase)

    @Test
    fun `start session validates questions and returns first question`() {
        val result = startQuizSessionUseCase(questions)

        assertTrue(result is AppResult.Success)
        val session = (result as AppResult.Success).data
        assertEquals(0, session.currentQuestionIndex)
        assertEquals(2, session.questions.size)
        assertFalse(session.isCompleted)
    }

    @Test
    fun `select answer records correctness and updates streak`() {
        val session = startSession()

        val result = selectAnswerUseCase(session, 1)

        assertTrue(result is AppResult.Success)
        val updatedSession = (result as AppResult.Success).data
        assertEquals(1, updatedSession.records.size)
        assertTrue(updatedSession.records.first().wasCorrect)
        assertEquals(1, updatedSession.currentStreak)
        assertEquals(1, updatedSession.longestStreak)
    }

    @Test
    fun `skip resets current streak and records skip`() {
        val answeredSession = ((selectAnswerUseCase(startSession(), 1) as AppResult.Success).data)

        val advancedSession = (advanceToNextQuestionUseCase(answeredSession) as AppResult.Success).data
        val skippedSession = (skipQuestionUseCase(advancedSession) as AppResult.Success).data

        assertEquals(0, skippedSession.currentStreak)
        assertTrue(skippedSession.records.last().wasSkipped)
        assertEquals(null, skippedSession.records.last().selectedOptionIndex)
    }

    @Test
    fun `advance after final resolved question completes session`() {
        val firstAnswered = (selectAnswerUseCase(startSession(), 1) as AppResult.Success).data
        val secondQuestion = (advanceToNextQuestionUseCase(firstAnswered) as AppResult.Success).data
        val secondAnswered = (selectAnswerUseCase(secondQuestion, 2) as AppResult.Success).data

        val completedSession = (advanceToNextQuestionUseCase(secondAnswered) as AppResult.Success).data

        assertTrue(completedSession.isCompleted)
        assertEquals(2, completedSession.currentQuestionIndex)
    }

    @Test
    fun `build result summarizes correct and skipped answers`() {
        val firstAnswered = (selectAnswerUseCase(startSession(), 1) as AppResult.Success).data
        val secondQuestion = (advanceToNextQuestionUseCase(firstAnswered) as AppResult.Success).data
        val secondSkipped = (skipQuestionUseCase(secondQuestion) as AppResult.Success).data
        val completedSession = (advanceToNextQuestionUseCase(secondSkipped) as AppResult.Success).data

        val result = buildQuizResultUseCase(completedSession)

        assertTrue(result is AppResult.Success)
        val summary = (result as AppResult.Success).data
        assertEquals(1, summary.correctAnswers)
        assertEquals(1, summary.skippedQuestions)
        assertEquals(1, summary.longestStreak)
        assertEquals(2, summary.totalQuestions)
    }

    @Test
    fun `build result returns error while quiz is still in progress`() {
        val inProgressSession = (selectAnswerUseCase(startSession(), 1) as AppResult.Success).data

        val result = buildQuizResultUseCase(inProgressSession)

        assertTrue(result is AppResult.Error)
        assertEquals(
            "Quiz results are unavailable before completion.",
            (result as AppResult.Error).message
        )
    }

    @Test
    fun `restart creates a fresh session with cleared progress`() {
        val firstAnswered = (selectAnswerUseCase(startSession(), 1) as AppResult.Success).data

        val restarted = restartQuizUseCase(firstAnswered)

        assertTrue(restarted is AppResult.Success)
        val restartedSession = (restarted as AppResult.Success).data
        assertEquals(0, restartedSession.currentQuestionIndex)
        assertTrue(restartedSession.records.isEmpty())
        assertEquals(0, restartedSession.currentStreak)
        assertEquals(0, restartedSession.longestStreak)
    }

    private fun startSession() = (startQuizSessionUseCase(questions) as AppResult.Success).data
}
