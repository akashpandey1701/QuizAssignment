package com.example.quizassignment.feature.quiz.presentation

import androidx.lifecycle.SavedStateHandle
import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.core.common.DispatchersProvider
import com.example.quizassignment.core.common.UiText
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.QuizSession
import com.example.quizassignment.domain.model.Subject
import com.example.quizassignment.domain.repository.QuizRepository
import com.example.quizassignment.domain.usecase.AdvanceToNextQuestionUseCase
import com.example.quizassignment.domain.usecase.GetQuizProgressUseCase
import com.example.quizassignment.domain.usecase.SaveQuizProgressUseCase
import com.example.quizassignment.domain.usecase.SelectAnswerUseCase
import com.example.quizassignment.domain.usecase.SkipQuestionUseCase
import com.example.quizassignment.feature.quiz.mapper.QuizUiMapper
import com.example.quizassignment.testutil.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
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
            savedStateHandle = SavedStateHandle(),
            selectAnswerUseCase = SelectAnswerUseCase(),
            skipQuestionUseCase = SkipQuestionUseCase(),
            advanceToNextQuestionUseCase = AdvanceToNextQuestionUseCase(),
            getQuizProgressUseCase = GetQuizProgressUseCase(
                quizRepository = FakeQuizRepository(),
                dispatchersProvider = TestDispatchersProvider(mainDispatcherRule.dispatcher)
            ),
            saveQuizProgressUseCase = SaveQuizProgressUseCase(
                quizRepository = FakeQuizRepository(),
                dispatchersProvider = TestDispatchersProvider(mainDispatcherRule.dispatcher)
            ),
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

    @Test
    fun `selecting an answer persists the current subject progress`() = runTest {
        val repository = FakeQuizRepository()
        val sessionStore = QuizSessionStore().apply {
            open(QuizViewModelTest.subject, validSession)
        }
        val dispatchers = TestDispatchersProvider(mainDispatcherRule.dispatcher)
        val viewModel = QuizViewModel(
            savedStateHandle = SavedStateHandle(mapOf("subjectId" to subject.id)),
            selectAnswerUseCase = SelectAnswerUseCase(),
            skipQuestionUseCase = SkipQuestionUseCase(),
            advanceToNextQuestionUseCase = AdvanceToNextQuestionUseCase(),
            getQuizProgressUseCase = GetQuizProgressUseCase(repository, dispatchers),
            saveQuizProgressUseCase = SaveQuizProgressUseCase(repository, dispatchers),
            quizUiMapper = QuizUiMapper(),
            quizSessionStore = sessionStore
        )
        runCurrent()

        viewModel.onEvent(QuizEvent.AnswerSelected(0))
        runCurrent()

        assertEquals(1, repository.savedProgress?.session?.records?.size)
        assertEquals(0, repository.savedProgress?.session?.currentQuestionIndex)
    }

    private class FakeQuizRepository : QuizRepository {
        var savedProgress: QuizProgress? = null

        override suspend fun getSubjects(): AppResult<List<Subject>> =
            AppResult.Success(emptyList())

        override suspend fun getQuestions(
            questionsUrl: String
        ): AppResult<List<Question>> = AppResult.Success(emptyList())

        override suspend fun getProgress(subjectId: String): AppResult<QuizProgress?> =
            AppResult.Success(null)

        override suspend fun saveProgress(progress: QuizProgress): AppResult<Unit> =
            AppResult.Success(Unit).also {
                savedProgress = progress
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

        val validSession = QuizSession(
            questions = validQuestions,
            currentQuestionIndex = 0,
            records = emptyList(),
            currentStreak = 0,
            longestStreak = 0
        )

        val subject = Subject(
            id = "android",
            title = "Android",
            description = "Android basics",
            questionsUrl = "https://example.com/android.json"
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
