package com.example.quizassignment.feature.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizassignment.feature.quiz.presentation.screen.QuizRoute
import com.example.quizassignment.feature.quiz.presentation.screen.ResultsRoute
import com.example.quizassignment.feature.subjects.presentation.screen.SubjectListRoute

@Composable
fun QuizNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = QuizRoutes.Subjects,
        modifier = modifier
    ) {
        composable(QuizRoutes.Subjects) {
            SubjectListRoute(
                onOpenQuiz = { subjectId ->
                    navController.navigate(QuizRoutes.quiz(subjectId)) {
                        launchSingleTop = true
                    }
                },
                onOpenResults = { subjectId ->
                    navController.navigate(QuizRoutes.results(subjectId)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = QuizRoutes.Quiz,
            arguments = listOf(
                navArgument(QuizRoutes.SubjectIdArgument) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val subjectId = requireNotNull(
                backStackEntry.arguments?.getString(QuizRoutes.SubjectIdArgument)
            )
            QuizRoute(
                onOpenResults = {
                    navController.navigate(QuizRoutes.results(subjectId)) {
                        popUpTo(QuizRoutes.Quiz) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRetryLoad = {
                    returnToSubjects(navController)
                }
            )
        }
        composable(
            route = QuizRoutes.Results,
            arguments = listOf(
                navArgument(QuizRoutes.SubjectIdArgument) { type = NavType.StringType }
            )
        ) {
            ResultsRoute(
                onBackToSubjects = { returnToSubjects(navController) }
            )
        }
    }
}

private fun returnToSubjects(navController: NavHostController) {
    navController.navigate(QuizRoutes.Subjects) {
        popUpTo(QuizRoutes.Subjects) { inclusive = true }
        launchSingleTop = true
    }
}
