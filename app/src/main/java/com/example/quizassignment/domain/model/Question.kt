package com.example.quizassignment.domain.model

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int
)
