package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.SubjectDto
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import org.junit.Assert.assertEquals
import org.junit.Test

class RetrofitQuizRemoteDataSourceTest {

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun `mixed payload keeps valid questions and skips invalid questions`() = runTest {
        val json = Json {
            allowTrailingComma = true
            ignoreUnknownKeys = true
            isLenient = true
        }
        val payload = json.parseToJsonElement(
            """
            [
              {
                "id": 1,
                "question": "Valid question",
                "options": ["A", "B", "C", "D"],
                "correctOptionIndex": 0
              },
              {
                "id": 2,
                "question": "Invalid question",
                "options": ["A", "B", "C", 10,],
                "correctOptionIndex": 1
              },
              {
                "id": 3,
                "question": "Legacy answer field",
                "options": ["A", "B", "C", "D"],
                "correctOption": 2
              }
            ]
            """.trimIndent()
        ).jsonArray
        val dataSource = RetrofitQuizRemoteDataSource(
            quizApi = FakeQuizApi(payload),
            json = json
        )

        val questions = dataSource.fetchQuestions("https://example.com/questions.json")

        assertEquals(listOf(1, 3), questions.map { it.id })
        assertEquals(2, questions.last().correctOptionIndex)
    }

    private class FakeQuizApi(
        private val questions: JsonArray
    ) : QuizApi {
        override suspend fun fetchSubjects(): List<SubjectDto> = emptyList()

        override suspend fun fetchQuestions(questionsUrl: String): JsonArray = questions
    }
}
