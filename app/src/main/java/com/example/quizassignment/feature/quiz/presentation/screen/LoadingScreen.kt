package com.example.quizassignment.feature.quiz.presentation.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quizassignment.R
import com.example.quizassignment.core.designsystem.components.AppEdgeToEdgeScaffold
import com.example.quizassignment.core.designsystem.components.PreviewFrame
import com.example.quizassignment.core.designsystem.theme.QuizPrimarySoft
import com.example.quizassignment.feature.quiz.presentation.LoadingUiState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(
    state: LoadingUiState,
    modifier: Modifier = Modifier
) {
    val preparingQuizLabel = stringResource(R.string.loading_preparing_quiz)
    AppEdgeToEdgeScaffold(modifier = modifier) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizPrimarySoft)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LoadingContent(
                isLoading = state.isLoading,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = preparingQuizLabel }
            )
        }
    }
}

@Composable
private fun LoadingContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_screen_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (isLoading) LottieConstants.IterateForever else 1,
        isPlaying = true
    )
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.75f) }

    LaunchedEffect(Unit) {
        logoAlpha.snapTo(0f)
        logoScale.snapTo(0.75f)
        delay(LogoRevealDelayMillis)
        coroutineScope {
            launch {
                logoAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = LogoRevealDurationMillis,
                        easing = FastOutSlowInEasing
                    )
                )
            }
            launch {
                logoScale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = 0.88f,
                        stiffness = 420f
                    )
                )
            }
        }
    }

    Box(modifier = modifier) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.ic_android_app_icon),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(96.dp)
                .graphicsLayer {
                    alpha = logoAlpha.value
                    scaleX = logoScale.value
                    scaleY = logoScale.value
                }
        )
    }
}

private const val LogoRevealDelayMillis = 434L
private const val LogoRevealDurationMillis = 440
