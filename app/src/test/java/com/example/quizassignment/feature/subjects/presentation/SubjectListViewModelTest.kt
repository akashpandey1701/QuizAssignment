package com.example.quizassignment.feature.subjects.presentation

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.core.common.DispatchersProvider
import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.domain.model.QuestionRecord
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.QuizSession
import com.example.quizassignment.domain.model.Subject
import com.example.quizassignment.domain.repository.QuizRepository
import com.example.quizassignment.domain.usecase.GetQuizProgressUseCase
import com.example.quizassignment.domain.usecase.LoadQuizQuestionsUseCase
import com.example.quizassignment.domain.usecase.LoadSubjectsUseCase
import com.example.quizassignment.domain.usecase.SaveQuizProgressUseCase
import com.example.quizassignment.domain.usecase.StartQuizSessionUseCase
import com.example.quizassignment.feature.quiz.presentation.QuizSessionStore
import com.example.quizassignment.testutil.MainDispatcherRule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SubjectListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `completed subject opens saved results without fetching questions`() = runTest {
        val repository = FakeQuizRepository(
            progress = QuizProgress(subject, completedSession)
        )
        val viewModel = createViewModel(repository)
        advanceUntilIdle()

        viewModel.selectSubject(subject.id)
        advanceUntilIdle()

        assertEquals(
            SubjectDestination(subject.id, SubjectDestinationScreen.Results),
            viewModel.uiState.value.destination
        )
        assertEquals(0, repository.questionCallCount)
    }

    @Test
    fun `new subject fetches its URL and saves resumable progress`() = runTest {
        val repository = FakeQuizRepository(progress = null)
        val sessionStore = QuizSessionStore()
        val viewModel = createViewModel(repository, sessionStore)
        advanceUntilIdle()

        viewModel.selectSubject(subject.id)
        advanceUntilIdle()

        assertEquals(subject.questionsUrl, repository.requestedQuestionsUrl)
        assertEquals(
            SubjectDestination(subject.id, SubjectDestinationScreen.Quiz),
            viewModel.uiState.value.destination
        )
        assertNotNull(repository.savedProgress)
        assertNotNull(sessionStore.currentSession())
    }

    private fun createViewModel(
        repository: QuizRepository,
        sessionStore: QuizSessionStore = QuizSessionStore()
    ): SubjectListViewModel {
        val dispatchers = TestDispatchersProvider(mainDispatcherRule.dispatcher)
        return SubjectListViewModel(
            loadSubjectsUseCase = LoadSubjectsUseCase(repository, dispatchers),
            loadQuizQuestionsUseCase = LoadQuizQuestionsUseCase(repository, dispatchers),
            getQuizProgressUseCase = GetQuizProgressUseCase(repository, dispatchers),
            saveQuizProgressUseCase = SaveQuizProgressUseCase(repository, dispatchers),
            startQuizSessionUseCase = StartQuizSessionUseCase(),
            quizSessionStore = sessionStore
        )
    }

    private class FakeQuizRepository(
        private var progress: QuizProgress?
    ) : QuizRepository {
        var questionCallCount: Int = 0
        var requestedQuestionsUrl: String? = null
        var savedProgress: QuizProgress? = null

        override suspend fun getSubjects(): AppResult<List<Subject>> =
            AppResult.Success(listOf(subject))

        override suspend fun getQuestions(
            questionsUrl: String
        ): AppResult<List<Question>> {
            questionCallCount++
            requestedQuestionsUrl = questionsUrl
            return AppResult.Success(questions)
        }

        override suspend fun getProgress(subjectId: String): AppResult<QuizProgress?> =
            AppResult.Success(progress)

        override suspend fun saveProgress(progress: QuizProgress): AppResult<Unit> {
            this.progress = progress
            savedProgress = progress
            return AppResult.Success(Unit)
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
        val subject = Subject(
            id = "android",
            title = "Android",
            description = "Android basics",
            questionsUrl = "https://example.com/android.json"
        )
        val questions = listOf(
            Question(
                id = 1,
                question = "Question",
                options = listOf("A", "B", "C", "D"),
                correctOptionIndex = 0
            )
        )
        val completedSession = QuizSession(
            questions = questions,
            currentQuestionIndex = 1,
            records = listOf(
                QuestionRecord(
                    questionId = 1,
                    selectedOptionIndex = 0,
                    wasSkipped = false,
                    wasCorrect = true
                )
            ),
            currentStreak = 1,
            longestStreak = 1
        )
    }
}
