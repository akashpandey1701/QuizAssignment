package com.example.quizassignment.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
