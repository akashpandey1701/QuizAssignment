package com.example.quizassignment.feature.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizassignment.feature.quiz.presentation.screen.LoadingRoute
import com.example.quizassignment.feature.quiz.presentation.screen.QuizRoute
import com.example.quizassignment.feature.quiz.presentation.screen.ResultsRoute

@Composable
fun QuizNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = QuizRoutes.Loading,
        modifier = modifier
    ) {
        composable(QuizRoutes.Loading) {
            LoadingRoute(
                onContinue = {
                    navController.navigate(QuizRoutes.Quiz) {
                        popUpTo(QuizRoutes.Loading) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(QuizRoutes.Quiz) {
            QuizRoute(
                onOpenResults = {
                    navController.navigate(QuizRoutes.Results) {
                        popUpTo(QuizRoutes.Quiz) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRetryLoad = {
                    navController.navigate(QuizRoutes.Loading) {
                        popUpTo(QuizRoutes.Quiz) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(QuizRoutes.Results) {
            ResultsRoute(
                onRestart = {
                    navController.navigate(QuizRoutes.Loading) {
                        popUpTo(QuizRoutes.Results) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
