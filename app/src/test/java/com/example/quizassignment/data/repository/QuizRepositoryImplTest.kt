package com.example.quizassignment.data.repository

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.data.datasource.remote.QuizRemoteDataSource
import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.data.mapper.QuestionMapper
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
        val repository = QuizRepositoryImpl(remote, QuestionMapper())

        val result = repository.getQuestions()

        assertTrue(result is AppResult.Success)
        val questions = (result as AppResult.Success).data
        assertEquals(1, questions.size)
        assertEquals("Remote question", questions.first().question)
        assertEquals(1, remote.callCount)
    }

    @Test
    fun `remote failure returns graceful error`() = runTest {
        val repository = QuizRepositoryImpl(
            remoteDataSource = FakeRemoteDataSource(error = IllegalStateException("network")),
            questionMapper = QuestionMapper()
        )

        val result = repository.getQuestions()

        assertTrue(result is AppResult.Error)
        assertEquals("Unable to load quiz questions.", (result as AppResult.Error).message)
    }

    @Test
    fun `empty payload returns error`() = runTest {
        val repository = QuizRepositoryImpl(
            remoteDataSource = FakeRemoteDataSource(questions = emptyList()),
            questionMapper = QuestionMapper()
        )

        val result = repository.getQuestions()

        assertTrue(result is AppResult.Error)
        assertEquals("Quiz question list is empty.", (result as AppResult.Error).message)
    }

    @Test
    fun `remote failure preserves underlying cause`() = runTest {
        val repository = QuizRepositoryImpl(
            remoteDataSource = FakeRemoteDataSource(error = IllegalStateException("remote")),
            questionMapper = QuestionMapper()
        )

        val result = repository.getQuestions()

        assertTrue(result is AppResult.Error)
        assertEquals("Unable to load quiz questions.", (result as AppResult.Error).message)
        assertEquals("remote", result.cause?.message)
    }

    @Test
    fun `remote cancellation is rethrown`() = runTest {
        val repository = QuizRepositoryImpl(
            remoteDataSource = FakeRemoteDataSource(error = CancellationException("cancelled")),
            questionMapper = QuestionMapper()
        )

        try {
            repository.getQuestions()
            fail("Expected cancellation to be rethrown.")
        } catch (_: CancellationException) {
        }
    }

    private class FakeRemoteDataSource(
        private val questions: List<QuestionDto> = emptyList(),
        private val error: Throwable? = null
    ) : QuizRemoteDataSource {
        var callCount: Int = 0

        override suspend fun fetchQuestions(): List<QuestionDto> {
            callCount++
            error?.let { throw it }
            return questions
        }
    }
}
