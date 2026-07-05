package com.example.quizassignment.feature.quiz.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizassignment.core.common.asString
import com.example.quizassignment.core.designsystem.components.AppEdgeToEdgeScaffold
import com.example.quizassignment.core.designsystem.components.PreviewFrame
import com.example.quizassignment.core.designsystem.theme.Ink900
import com.example.quizassignment.core.designsystem.theme.QuizHeaderBackground
import com.example.quizassignment.core.designsystem.theme.QuizIncorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizPrimaryText
import com.example.quizassignment.core.designsystem.theme.QuizQuestionTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizSupportingTextStyle
import com.example.quizassignment.core.designsystem.theme.White
import com.example.quizassignment.feature.quiz.presentation.QuestionUiModel
import com.example.quizassignment.feature.quiz.presentation.QuizEvent
import com.example.quizassignment.feature.quiz.presentation.QuizUiState
import com.example.quizassignment.feature.quiz.presentation.QuizViewModel
import com.example.quizassignment.feature.quiz.presentation.components.AnswerOptionCard
import com.example.quizassignment.feature.quiz.presentation.components.AutoAdvanceProgress
import com.example.quizassignment.feature.quiz.presentation.components.QuizHeader
import kotlinx.coroutines.delay

@Composable
fun QuizRoute(
    onOpenResults: () -> Unit,
    onRetryLoad: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(state.shouldNavigateToResults) {
        if (state.shouldNavigateToResults) {
            onOpenResults()
        }
    }

    QuizScreen(
        state = state,
        onAnswerSelected = { viewModel.onEvent(QuizEvent.AnswerSelected(it)) },
        onSkipClicked = { viewModel.onEvent(QuizEvent.SkipClicked) },
        onRetryLoad = onRetryLoad
    )
}

@Composable
fun QuizScreen(
    state: QuizUiState,
    onAnswerSelected: (Int) -> Unit,
    onSkipClicked: () -> Unit,
    onRetryLoad: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AppEdgeToEdgeScaffold(modifier = modifier) { innerPadding ->
        if (!state.isSessionReady) {
            if (state.errorMessage != null) {
                QuizLoadFailedScreen(
                    onRetryLoad = onRetryLoad,
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(QuizHeaderBackground)
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.supportingText.asString(context),
                        style = QuizSupportingTextStyle,
                        color = QuizPrimaryText
                    )
                }
            }
            return@AppEdgeToEdgeScaffold
        }

        val layoutDirection = LocalLayoutDirection.current
        val safeTopInset = innerPadding.calculateTopPadding()
        val safeBottomInset = innerPadding.calculateBottomPadding()
        val safeStartInset = innerPadding.calculateStartPadding(layoutDirection)
        val safeEndInset = innerPadding.calculateEndPadding(layoutDirection)

        val sheetState = QuestionSheetContentState(
            question = state.currentQuestion,
            selectedOptionIndex = state.selectedOptionIndex,
            correctOptionIndex = state.correctOptionIndex,
            isAnswerRevealed = state.isAnswerRevealed,
            canAnswer = state.canAnswer,
            errorMessage = state.errorMessage?.asString(context)
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizHeaderBackground)
        ) {
            val isExpanded = maxWidth >= 760.dp
            val sheetHeight = maxHeight * QuestionSheetHeightFraction
            val headerHeight = maxHeight - sheetHeight
            val horizontalPadding = if (isExpanded) 32.dp else 24.dp
            val headerTopSpacing = if (isExpanded) 12.dp else 14.dp
            val headerBottomSpacing = if (isExpanded) 18.dp else 16.dp

            GlassyTopOverlay(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(headerHeight + 40.dp)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(headerHeight)
                    .padding(
                        start = safeStartInset + horizontalPadding,
                        end = safeEndInset + horizontalPadding
                    ),
                contentAlignment = Alignment.TopCenter
            ) {
                QuizHeader(
                    questionNumber = state.currentQuestion.questionNumber,
                    totalQuestions = state.currentQuestion.totalQuestions,
                    currentStreak = state.currentStreak,
                    isStreakHighlighted = state.isStreakHighlighted,
                    canSkip = state.canSkip,
                    onSkipClicked = onSkipClicked,
                    modifier = Modifier
                        .widthIn(max = 720.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            top = safeTopInset + headerTopSpacing,
                            bottom = headerBottomSpacing
                        )
                )
            }

            QuestionSheet(
                state = sheetState,
                safeBottomInset = safeBottomInset,
                safeStartInset = safeStartInset,
                safeEndInset = safeEndInset,
                onAnswerSelected = onAnswerSelected,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(sheetHeight)
            )
        }
    }
}

@Composable
private fun QuestionSheet(
    state: QuestionSheetContentState,
    safeBottomInset: Dp,
    safeStartInset: Dp,
    safeEndInset: Dp,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var displayedState by remember { mutableStateOf(state) }
    val scrollState = remember(displayedState.question.id) { ScrollState(initial = 0) }
    var visibleOptionCount by remember { mutableIntStateOf(0) }
    var isQuestionVisible by remember { mutableStateOf(false) }
    var hasAnimatedInitialQuestion by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state.question.id == displayedState.question.id) {
            displayedState = state
        }
    }

    LaunchedEffect(state.question.id) {
        if (!hasAnimatedInitialQuestion) {
            displayedState = state
            visibleOptionCount = 0
            isQuestionVisible = false
            delay(QuestionAppearDelayMillis.toLong())
            isQuestionVisible = true
            delay((IncomingQuestionDurationMillis ).toLong())
            repeat(displayedState.question.options.size) { optionIndex ->
                visibleOptionCount = optionIndex + 1
                if (optionIndex != displayedState.question.options.lastIndex) {
                    delay(OptionStaggerDelayMillis.toLong())
                }
            }
            hasAnimatedInitialQuestion = true
            return@LaunchedEffect
        }

        if (state.question.id == displayedState.question.id) {
            return@LaunchedEffect
        }

        while (visibleOptionCount > 0) {
            visibleOptionCount -= 1
            delay(OptionExitStaggerDelayMillis.toLong())
        }

        delay(QuestionExitDelayAfterOptionsMillis.toLong())
        isQuestionVisible = false
        delay(OutgoingQuestionDurationMillis.toLong())

        displayedState = state
        delay(QuestionAppearDelayMillis.toLong())
        isQuestionVisible = true
        delay((IncomingQuestionDurationMillis + QuestionBeforeOptionsDelayMillis).toLong())
        repeat(displayedState.question.options.size) { optionIndex ->
            visibleOptionCount = optionIndex + 1
            if (optionIndex != displayedState.question.options.lastIndex) {
                delay(OptionStaggerDelayMillis.toLong())
            }
        }
    }

    Surface(
        modifier = modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp
        ),
        color = White,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 720.dp)
                    .fillMaxSize()
                    .padding(
                        start = safeStartInset + 24.dp,
                        top = 28.dp,
                        end = safeEndInset + 24.dp,
                        bottom = safeBottomInset + 18.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(22.dp)
                ) {
                    AnimatedVisibility(
                        visible = isQuestionVisible,
                        enter = slideInVertically(
                            animationSpec = tween(
                                durationMillis = IncomingQuestionDurationMillis,
                                easing = FastOutSlowInEasing
                            ),
                            initialOffsetY = { fullHeight -> fullHeight / 5 }
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = IncomingQuestionDurationMillis,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(durationMillis = OutgoingQuestionDurationMillis)
                        )
                    ) {
                        Text(
                            text = displayedState.question.title,
                            style = QuizQuestionTextStyle,
                            color = Ink900,
                            modifier = Modifier.semantics { heading() }
                        )
                    }

                    displayedState.errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = QuizIncorrectAccent,
                            style = QuizSupportingTextStyle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { liveRegion = LiveRegionMode.Assertive }
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        displayedState.question.options.forEachIndexed { index, option ->
                            AnimatedVisibility(
                                visible = visibleOptionCount > index,
                                enter = slideInVertically(
                                    animationSpec = tween(
                                        durationMillis = IncomingOptionDurationMillis,
                                        easing = FastOutSlowInEasing
                                    ),
                                    initialOffsetY = { fullHeight -> fullHeight / 10 }
                                ) + fadeIn(
                                    animationSpec = tween(
                                        durationMillis = IncomingOptionDurationMillis,
                                        easing = FastOutSlowInEasing
                                    )
                                ),
                                exit = fadeOut(
                                    animationSpec = tween(durationMillis = OutgoingOptionDurationMillis)
                                )
                            ) {
                                AnswerOptionCard(
                                    label = option,
                                    optionLabel = ('A' + index).toString(),
                                    isSelected = displayedState.selectedOptionIndex == index,
                                    isCorrect = displayedState.correctOptionIndex == index,
                                    isAnswerRevealed = displayedState.isAnswerRevealed,
                                    enabled = displayedState.canAnswer,
                                    onClick = { onAnswerSelected(index) }
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    visible = displayedState.isAnswerRevealed,
                    enter = slideInVertically(
                        animationSpec = tween(durationMillis = 180, easing = FastOutSlowInEasing),
                        initialOffsetY = { fullHeight -> fullHeight / 2 }
                    ) + fadeIn(animationSpec = tween(durationMillis = 180)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 120))
                ) {
                    AutoAdvanceProgress(
                        isAnswerRevealed = displayedState.isAnswerRevealed,
                        currentQuestionId = displayedState.question.id,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

private data class QuestionSheetContentState(
    val question: QuestionUiModel,
    val selectedOptionIndex: Int?,
    val correctOptionIndex: Int?,
    val isAnswerRevealed: Boolean,
    val canAnswer: Boolean,
    val errorMessage: String?
)
private const val QuestionSheetHeightFraction = 0.79f
private const val QuestionAppearDelayMillis = 90
private const val IncomingQuestionDurationMillis = 420
private const val QuestionBeforeOptionsDelayMillis = 20
private const val IncomingOptionDurationMillis = 10
private const val OptionStaggerDelayMillis = 110
private const val OptionExitStaggerDelayMillis = 35
private const val QuestionExitDelayAfterOptionsMillis = 55
private const val OutgoingOptionDurationMillis = 150
private const val OutgoingQuestionDurationMillis = 420

@Composable
private fun GlassyTopOverlay(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            White.copy(alpha = 0.24f),
                            White.copy(alpha = 0.10f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth(0.72f)
                .fillMaxHeight(0.78f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            White.copy(alpha = 0.22f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}
