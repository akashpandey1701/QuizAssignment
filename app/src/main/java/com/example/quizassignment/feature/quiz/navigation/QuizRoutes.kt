package com.example.quizassignment.feature.quiz.navigation

import android.net.Uri

object QuizRoutes {
    const val Subjects = "subjects"
    const val SubjectIdArgument = "subjectId"
    const val Quiz = "quiz/{$SubjectIdArgument}"
    const val Results = "results/{$SubjectIdArgument}"

    fun quiz(subjectId: String): String = "quiz/${Uri.encode(subjectId)}"
    fun results(subjectId: String): String = "results/${Uri.encode(subjectId)}"
}
