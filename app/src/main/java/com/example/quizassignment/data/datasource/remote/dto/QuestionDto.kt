package com.example.quizassignment.data.datasource.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class QuestionDto(
    val id: Int,
    val question: String,
    val options: List<String>,
    @JsonNames("correctOption")
    val correctOptionIndex: Int
)
