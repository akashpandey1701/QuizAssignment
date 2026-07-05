package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.core.common.UiText
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuizSession
import com.example.quizassignment.domain.usecase.AdvanceToNextQuestionUseCase
import com.example.quizassignment.domain.usecase.SelectAnswerUseCase
import com.example.quizassignment.domain.usecase.SkipQuestionUseCase
import com.example.quizassignment.feature.quiz.mapper.QuizUiMapper
import com.example.quizassignment.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `select answer error is surfaced and cleared on next valid session update`() = runTest {
        val sessionStore = QuizSessionStore().apply {
            update(invalidSession)
        }
        val viewModel = QuizViewModel(
            selectAnswerUseCase = SelectAnswerUseCase(),
            skipQuestionUseCase = SkipQuestionUseCase(),
            advanceToNextQuestionUseCase = AdvanceToNextQuestionUseCase(),
            quizUiMapper = QuizUiMapper(),
            quizSessionStore = sessionStore
        )

        advanceUntilIdle()
        viewModel.onEvent(QuizEvent.AnswerSelected(0))

        assertEquals(
            UiText.Dynamic("Current question is unavailable."),
            viewModel.uiState.value.errorMessage
        )

        sessionStore.update(validSession)
        advanceUntilIdle()

        assertNull(viewModel.uiState.value.errorMessage)
        assertEquals("Question one", viewModel.uiState.value.currentQuestion.title)
    }

    private companion object {
        val validQuestions = listOf(
            Question(
                id = 1,
                question = "Question one",
                options = listOf("A", "B", "C", "D"),
                correctOptionIndex = 0
            ),
            Question(
                id = 2,
                question = "Question two",
                options = listOf("A", "B", "C", "D"),
                correctOptionIndex = 1
            )
        )

        val validSession = QuizSession(
            questions = validQuestions,
            currentQuestionIndex = 0,
            records = emptyList(),
            currentStreak = 0,
            longestStreak = 0
        )

        val invalidSession = QuizSession(
            questions = validQuestions,
            currentQuestionIndex = -1,
            records = emptyList(),
            currentStreak = 0,
            longestStreak = 0
        )
    }
}
