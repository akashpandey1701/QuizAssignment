package com.example.quizassignment.feature.quiz.mapper

import com.example.quizassignment.domain.model.Question
import com.example.quizassignment.feature.quiz.presentation.QuestionUiModel
import javax.inject.Inject

class QuizUiMapper @Inject constructor() {

    fun map(question: Question, number: Int, total: Int): QuestionUiModel = QuestionUiModel(
        id = question.id,
        questionNumber = number,
        totalQuestions = total,
        title = question.question,
        options = question.options
    )
}
