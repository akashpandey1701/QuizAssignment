package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.core.common.DispatchersProvider
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.repository.QuizRepository
import com.example.quizassignment.domain.usecase.LoadQuizQuestionsUseCase
import com.example.quizassignment.domain.usecase.StartQuizSessionUseCase
import com.example.quizassignment.testutil.MainDispatcherRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `successful load prepares session and navigates to quiz`() = runTest {
        val repository = FakeQuizRepository(
            result = AppResult.Success(validQuestions)
        )
        val sessionStore = QuizSessionStore()

        val viewModel = LoadingViewModel(
            loadQuizQuestionsUseCase = LoadQuizQuestionsUseCase(
                quizRepository = repository,
                dispatchersProvider = TestDispatchersProvider(mainDispatcherRule.dispatcher)
            ),
            startQuizSessionUseCase = StartQuizSessionUseCase(),
            quizSessionStore = sessionStore
        )

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.shouldNavigateToQuiz)
        assertFalse(viewModel.uiState.value.isLoading)
        assertNotNull(sessionStore.currentSession())
        assertEquals(1, repository.callCount)
    }

    @Test
    fun `load failure stores error and routes to the quiz error screen`() = runTest {
        val repository = FakeQuizRepository(
            result = AppResult.Error(message = "Unable to load quiz questions.")
        )
        val sessionStore = QuizSessionStore()

        val viewModel = LoadingViewModel(
            loadQuizQuestionsUseCase = LoadQuizQuestionsUseCase(
                quizRepository = repository,
                dispatchersProvider = TestDispatchersProvider(mainDispatcherRule.dispatcher)
            ),
            startQuizSessionUseCase = StartQuizSessionUseCase(),
            quizSessionStore = sessionStore
        )

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.shouldNavigateToQuiz)
        assertFalse(viewModel.uiState.value.isLoading)
        assertEquals(null, sessionStore.currentSession())
        assertEquals("Unable to load quiz questions.", sessionStore.loadError.value)
    }

    @Test
    fun `existing session skips reload and navigates immediately`() = runTest {
        val repository = FakeQuizRepository(
            result = AppResult.Success(validQuestions)
        )
        val sessionStore = QuizSessionStore().apply {
            update(
                StartQuizSessionUseCase().invoke(validQuestions).let {
                    (it as AppResult.Success).data
                }
            )
        }

        val viewModel = LoadingViewModel(
            loadQuizQuestionsUseCase = LoadQuizQuestionsUseCase(
                quizRepository = repository,
                dispatchersProvider = TestDispatchersProvider(mainDispatcherRule.dispatcher)
            ),
            startQuizSessionUseCase = StartQuizSessionUseCase(),
            quizSessionStore = sessionStore
        )

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.shouldNavigateToQuiz)
        assertEquals(0, repository.callCount)
    }

    private class FakeQuizRepository(
        private val result: AppResult<List<Question>>
    ) : QuizRepository {
        var callCount: Int = 0

        override suspend fun getQuestions(): AppResult<List<Question>> {
            callCount++
            return result
        }
    }

    private class TestDispatchersProvider(
        private val dispatcher: CoroutineDispatcher
    ) : DispatchersProvider {
        override val io: CoroutineDispatcher = dispatcher
        override val default: CoroutineDispatcher = dispatcher
        override val main: CoroutineDispatcher = dispatcher
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
    }
}
