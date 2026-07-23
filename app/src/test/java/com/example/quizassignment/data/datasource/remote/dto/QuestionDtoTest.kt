package com.example.quizassignment.data.datasource.remote.dto

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class QuestionDtoTest {

    @Test
    fun `legacy correctOption field is decoded as correct option index`() {
        val payload = """
            {
              "id": 7,
              "question": "What updates Compose UI?",
              "options": ["A", "B", "C", "D"],
              "correctOption": 2
            }
        """.trimIndent()

        val question = Json.decodeFromString<QuestionDto>(payload)

        assertEquals(2, question.correctOptionIndex)
    }
}
