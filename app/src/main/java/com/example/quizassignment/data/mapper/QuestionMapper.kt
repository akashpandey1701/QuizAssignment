package com.example.quizassignment.data.mapper

import com.example.quizassignment.data.datasource.remote.dto.QuestionDto
import com.example.quizassignment.domain.model.Question
import javax.inject.Inject

class QuestionMapper @Inject constructor() {

    fun map(input: QuestionDto): Question = Question(
        id = input.id,
        question = input.question,
        options = input.options,
        correctOptionIndex = input.correctOptionIndex
    )
}
