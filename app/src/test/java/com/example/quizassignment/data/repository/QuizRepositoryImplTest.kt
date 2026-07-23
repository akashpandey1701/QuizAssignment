package com.example.quizassignment.data.repository

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.data.datasource.local.QuizProgressLocalDataSource
import com.example.quizassignment.data.datasource.remote.QuizRemoteDataSource
import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.data.datasource.remote.dto.SubjectDto
import com.example.quizassignment.data.mapper.QuestionMapper
import com.example.quizassignment.data.mapper.SubjectMapper
import com.example.quizassignment.domain.model.QuizProgress
import java.util.concurrent.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class QuizRepositoryImplTest {

    @Test
    fun `remote success returns mapped questions`() = runTest {
        val remote = FakeRemoteDataSource(
            questions = listOf(
                QuestionDto(
                    id = 1,
                    question = "Remote question",
                    options = listOf("A", "B", "C", "D"),
                    correctOptionIndex = 2
                )
            )
        )
        val repository = createRepository(remote)

        val result = repository.getQuestions(QuestionsUrl)

        assertTrue(result is AppResult.Success)
        val questions = (result as AppResult.Success).data
        assertEquals(1, questions.size)
        assertEquals("Remote question", questions.first().question)
        assertEquals(1, remote.callCount)
    }

    @Test
    fun `remote failure returns graceful error`() = runTest {
        val repository = createRepository(
            FakeRemoteDataSource(error = IllegalStateException("network"))
        )

        val result = repository.getQuestions(QuestionsUrl)

        assertTrue(result is AppResult.Error)
        assertEquals("Unable to load quiz questions.", (result as AppResult.Error).message)
    }

    @Test
    fun `empty payload returns error`() = runTest {
        val repository = createRepository(FakeRemoteDataSource(questions = emptyList()))

        val result = repository.getQuestions(QuestionsUrl)

        assertTrue(result is AppResult.Error)
        assertEquals("Quiz question list is empty.", (result as AppResult.Error).message)
    }

    @Test
    fun `remote failure preserves underlying cause`() = runTest {
        val repository = createRepository(
            FakeRemoteDataSource(error = IllegalStateException("remote"))
        )

        val result = repository.getQuestions(QuestionsUrl)

        assertTrue(result is AppResult.Error)
        assertEquals("Unable to load quiz questions.", (result as AppResult.Error).message)
        assertEquals("remote", result.cause?.message)
    }

    @Test
    fun `remote cancellation is rethrown`() = runTest {
        val repository = createRepository(
            FakeRemoteDataSource(error = CancellationException("cancelled"))
        )

        try {
            repository.getQuestions(QuestionsUrl)
            fail("Expected cancellation to be rethrown.")
        } catch (_: CancellationException) {
        }
    }

    @Test
    fun `subject payload is mapped by the existing repository`() = runTest {
        val repository = createRepository(
            FakeRemoteDataSource(
                subjects = listOf(
                    SubjectDto(
                        id = "android",
                        title = "Android",
                        description = "Android basics",
                        questionsUrl = "https://example.com/android.json"
                    )
                )
            )
        )

        val result = repository.getSubjects()

        assertTrue(result is AppResult.Success)
        assertEquals("Android", (result as AppResult.Success).data.single().title)
    }

    private fun createRepository(
        remoteDataSource: QuizRemoteDataSource
    ): QuizRepositoryImpl = QuizRepositoryImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = FakeLocalDataSource(),
        questionMapper = QuestionMapper(),
        subjectMapper = SubjectMapper()
    )

    private class FakeRemoteDataSource(
        private val questions: List<QuestionDto> = emptyList(),
        private val subjects: List<SubjectDto> = emptyList(),
        private val error: Throwable? = null
    ) : QuizRemoteDataSource {
        var callCount: Int = 0

        override suspend fun fetchSubjects(): List<SubjectDto> {
            error?.let { throw it }
            return subjects
        }

        override suspend fun fetchQuestions(questionsUrl: String): List<QuestionDto> {
            callCount++
            error?.let { throw it }
            return questions
        }
    }

    private class FakeLocalDataSource : QuizProgressLocalDataSource {
        override suspend fun getProgress(subjectId: String): QuizProgress? = null

        override suspend fun saveProgress(progress: QuizProgress) = Unit
    }

    private companion object {
        const val QuestionsUrl = "https://example.com/questions.json"
    }
}
