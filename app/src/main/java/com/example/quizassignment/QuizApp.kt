package com.example.quizassignment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.quizassignment.feature.quiz.navigation.QuizNavHost

@Composable
fun QuizApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    QuizNavHost(
        navController = navController,
        modifier = modifier
    )
}
