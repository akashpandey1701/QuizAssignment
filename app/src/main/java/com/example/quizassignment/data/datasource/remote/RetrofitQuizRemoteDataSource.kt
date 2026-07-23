package com.example.quizassignment.data.datasource.remote

import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.data.datasource.remote.dto.SubjectDto
import javax.inject.Inject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.intOrNull

class RetrofitQuizRemoteDataSource @Inject constructor(
    private val quizApi: QuizApi,
    private val json: Json
) : QuizRemoteDataSource {

    override suspend fun fetchSubjects(): List<SubjectDto> = quizApi.fetchSubjects()

    override suspend fun fetchQuestions(questionsUrl: String): List<QuestionDto> {
        return quizApi.fetchQuestions(questionsUrl).mapNotNull { questionJson ->
            if (!questionJson.hasValidQuestionShape()) return@mapNotNull null
            runCatching {
                json.decodeFromJsonElement<QuestionDto>(questionJson)
            }.getOrNull()
        }
    }

    private fun JsonElement.hasValidQuestionShape(): Boolean {
        val questionObject = this as? JsonObject ?: return false
        val id = questionObject["id"] as? JsonPrimitive ?: return false
        val question = questionObject["question"] as? JsonPrimitive ?: return false
        val options = questionObject["options"] as? JsonArray ?: return false
        val correctOption = (
            questionObject["correctOptionIndex"] ?: questionObject["correctOption"]
            ) as? JsonPrimitive ?: return false

        return id.intOrNull != null &&
            question.isString &&
            question.content.isNotBlank() &&
            options.size == RequiredOptionCount &&
            options.all { option ->
                option is JsonPrimitive && option.isString && option.content.isNotBlank()
            } &&
            correctOption.intOrNull in options.indices
    }

    private companion object {
        const val RequiredOptionCount = 4
    }
}
