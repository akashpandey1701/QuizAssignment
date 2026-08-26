package com.example.quizassignment.feature.quiz.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
    onPaymentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = QuizRoutes.Subjects,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        modifier = modifier
    ) {
        composable(QuizRoutes.Subjects) {
            SubjectListRoute(
                onPaymentClick = onPaymentClick,
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
