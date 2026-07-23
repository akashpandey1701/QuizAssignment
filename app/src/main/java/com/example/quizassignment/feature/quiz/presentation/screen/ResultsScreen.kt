package com.example.quizassignment.feature.quiz.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizassignment.R
import com.example.quizassignment.core.common.UiText
import com.example.quizassignment.core.common.asString
import com.example.quizassignment.core.designsystem.components.AppEdgeToEdgeScaffold
import com.example.quizassignment.core.designsystem.components.PreviewFrame
import com.example.quizassignment.core.designsystem.theme.QuizCorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizCorrectBackground
import com.example.quizassignment.core.designsystem.theme.QuizGoldWash
import com.example.quizassignment.core.designsystem.theme.QuizHeaderBackground
import com.example.quizassignment.core.designsystem.theme.QuizIncorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizIncorrectBackground
import com.example.quizassignment.core.designsystem.theme.QuizOptionBorder
import com.example.quizassignment.core.designsystem.theme.QuizOptionSurface
import com.example.quizassignment.core.designsystem.theme.QuizPrimary
import com.example.quizassignment.core.designsystem.theme.QuizPrimarySoft
import com.example.quizassignment.core.designsystem.theme.QuizPrimaryText
import com.example.quizassignment.core.designsystem.theme.QuizSecondaryText
import com.example.quizassignment.core.designsystem.theme.QuizStreakActiveIcon
import com.example.quizassignment.core.designsystem.theme.QuizTimerTrack
import com.example.quizassignment.core.designsystem.theme.ResultsButtonTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsEyebrowTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsHeadlineTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsSectionTitleTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsStatLabelTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsStatValueTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsSupportingTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsTimelineNumberTextStyle
import com.example.quizassignment.core.designsystem.theme.ResultsTimelineStatusTextStyle
import com.example.quizassignment.core.designsystem.theme.White
import com.example.quizassignment.feature.quiz.presentation.QuestionResultStatus
import com.example.quizassignment.feature.quiz.presentation.QuestionResultUiModel
import com.example.quizassignment.feature.quiz.presentation.ResultsUiState
import com.example.quizassignment.feature.quiz.presentation.ResultsViewModel
import kotlinx.coroutines.delay

@Composable
fun ResultsRoute(
    onBackToSubjects: () -> Unit,
    viewModel: ResultsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    ResultsScreen(
        state = state,
        onBackToSubjects = onBackToSubjects
    )
}

@Composable
fun ResultsScreen(
    state: ResultsUiState,
    onBackToSubjects: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(enabled = true) {
        onBackToSubjects()
    }

    AppEdgeToEdgeScaffold(modifier = modifier) { innerPadding ->
        val layoutDirection = LocalLayoutDirection.current
        val scrollState = rememberScrollState()

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(QuizHeaderBackground)
        ) {
            val horizontalPadding = if (maxWidth >= 760.dp) 32.dp else 20.dp

            ResultsGlassOverlay(modifier = Modifier.matchParentSize())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(
                        start = innerPadding.calculateStartPadding(layoutDirection) + horizontalPadding,
                        top = innerPadding.calculateTopPadding() + 24.dp,
                        end = innerPadding.calculateEndPadding(layoutDirection) + horizontalPadding,
                        bottom = innerPadding.calculateBottomPadding() + 28.dp
                    )
                    .widthIn(max = 760.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                ResultsHeroSection(state = state)
                ResultsInsightSection(state = state)
                JourneySection(
                    questionResults = state.questionResults,
                    modifier = Modifier.fillMaxWidth()
                )
                BackToSubjectsSection(onBackToSubjects = onBackToSubjects)
            }
        }
    }
}

@Composable
private fun ResultsHeroSection(
    state: ResultsUiState,
    modifier: Modifier = Modifier
) {
    val milestoneCount = state.questionResults.count { it.completesStreakMilestone }

    ResultsPanel(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        backgroundBrush = Brush.verticalGradient(
            colors = listOf(
                White.copy(alpha = 0.96f),
                QuizOptionSurface.copy(alpha = 0.98f)
            )
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            QuizPrimarySoft.copy(alpha = 0.95f),
                            Color.Transparent
                        ),
                        center = Offset(120f, 0f),
                        radius = 650f
                    )
                )
                .padding(horizontal = 22.dp, vertical = 22.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(116.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                White.copy(alpha = 0.36f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )

            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val compact = maxWidth < 560.dp
                if (compact) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ScoreOrb(state = state)
                        HeroCopy(
                            state = state,
                            milestoneCount = milestoneCount,
                            textAlign = TextAlign.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(22.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ScoreOrb(state = state)
                        HeroCopy(
                            state = state,
                            milestoneCount = milestoneCount,
                            textAlign = TextAlign.Start,
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeroCopy(
    state: ResultsUiState,
    milestoneCount: Int,
    textAlign: TextAlign,
    horizontalAlignment: Alignment.Horizontal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = state.statusLabel.asString(LocalContext.current),
            style = ResultsEyebrowTextStyle,
            color = QuizPrimary,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = state.headline.asString(LocalContext.current),
            style = ResultsHeadlineTextStyle,
            color = QuizPrimaryText,
            textAlign = textAlign,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { heading() }
        )
        Text(
            text = state.supportingText.asString(LocalContext.current),
            style = ResultsSupportingTextStyle,
            color = QuizSecondaryText,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (textAlign == TextAlign.Center) {
                Arrangement.Center
            } else {
                Arrangement.Start
            }
        ) {
            ResultHighlightChip(
                label = stringResource(R.string.results_chip_best_streak),
                value = state.longestStreak.toString(),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.LocalFireDepartment,
                        contentDescription = null,
                        tint = QuizStreakActiveIcon,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
            ResultHighlightChip(
                label = stringResource(R.string.results_chip_milestones),
                value = milestoneCount.toString(),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = QuizPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
        }
        MomentumBand(state = state)
    }
}

@Composable
private fun ScoreOrb(
    state: ResultsUiState,
    modifier: Modifier = Modifier
) {
    val accuracyDescription = stringResource(
        R.string.results_accuracy_description,
        state.accuracyPercent,
        state.correctAnswers,
        state.totalQuestions
    )
    val progress = (state.accuracyPercent / 100f).coerceIn(0f, 1f)
    Box(
        modifier = modifier
            .size(176.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = accuracyDescription
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 16.dp.toPx()
            drawCircle(
                color = QuizTimerTrack.copy(alpha = 0.42f),
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        QuizCorrectAccent,
                        QuizPrimary,
                        QuizCorrectAccent
                    )
                ),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawCircle(
                color = White.copy(alpha = 0.82f),
                radius = size.minDimension * 0.33f
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(R.string.results_accuracy_percent, state.accuracyPercent),
                style = ResultsHeadlineTextStyle,
                color = QuizPrimaryText
            )
            Text(
                text = stringResource(
                    R.string.results_accuracy_correct_count,
                    state.correctAnswers,
                    state.totalQuestions
                ),
                style = ResultsStatLabelTextStyle,
                color = QuizSecondaryText
            )
        }
    }
}

@Composable
private fun MomentumBand(
    state: ResultsUiState,
    modifier: Modifier = Modifier
) {
    val rhythmLabel = when {
        state.accuracyPercent >= 90 -> stringResource(R.string.results_momentum_elite)
        state.accuracyPercent >= 75 -> stringResource(R.string.results_momentum_strong)
        state.accuracyPercent >= 60 -> stringResource(R.string.results_momentum_steady)
        else -> stringResource(R.string.results_momentum_practice)
    }
    val summary = when {
        state.skippedQuestions == 0 -> stringResource(R.string.results_momentum_no_skips)
        state.skippedQuestions == 1 -> stringResource(R.string.results_momentum_one_skip)
        else -> pluralStringResource(
            R.plurals.results_momentum_skips,
            state.skippedQuestions,
            state.skippedQuestions
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White.copy(alpha = 0.58f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = rhythmLabel,
                style = ResultsSectionTitleTextStyle,
                color = QuizPrimaryText,
                textAlign = TextAlign.Start
            )
            Text(
                text = summary,
                style = ResultsStatLabelTextStyle,
                color = QuizSecondaryText,
                textAlign = TextAlign.Start
            )
        }
        Icon(
            imageVector = Icons.Filled.Timeline,
            contentDescription = null,
            tint = QuizPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun ResultHighlightChip(
    label: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = White.copy(alpha = 0.74f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = value,
                style = ResultsSectionTitleTextStyle,
                color = QuizPrimaryText
            )
            Text(
                text = label,
                style = ResultsStatLabelTextStyle,
                color = QuizSecondaryText
            )
        }
    }
}

@Composable
private fun ResultsInsightSection(
    state: ResultsUiState,
    modifier: Modifier = Modifier
) {
    val insights = state.toInsights()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.results_section_performance_snapshot),
            style = ResultsSectionTitleTextStyle,
            color = QuizPrimaryText
        )
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val compact = maxWidth < 560.dp
            if (compact) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    insights.forEach { insight ->
                        InsightCard(insight = insight, modifier = Modifier.fillMaxWidth())
                    }
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    insights.forEach { insight ->
                        InsightCard(
                            insight = insight,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InsightCard(
    insight: ResultsInsight,
    modifier: Modifier = Modifier
) {
    ResultsPanel(
        modifier = modifier,
        backgroundColor = insight.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(White.copy(alpha = 0.84f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = insight.icon,
                        contentDescription = null,
                        tint = insight.accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = insight.title,
                    style = ResultsSectionTitleTextStyle,
                    color = QuizPrimaryText
                )
            }
            Text(
                text = insight.value,
                style = ResultsStatValueTextStyle,
                color = QuizPrimaryText
            )
            Text(
                text = insight.supporting,
                style = ResultsStatLabelTextStyle,
                color = QuizSecondaryText
            )
        }
    }
}

@Composable
private fun JourneySection(
    questionResults: List<QuestionResultUiModel>,
    modifier: Modifier = Modifier
) {
    var visibleCount by remember(questionResults) { mutableIntStateOf(0) }

    LaunchedEffect(questionResults) {
        visibleCount = 0
        questionResults.forEachIndexed { index, _ ->
            delay(if (index == 0) 80L else 60L)
            visibleCount = index + 1
        }
    }

    val correctCount = questionResults.count { it.status == QuestionResultStatus.Correct }
    val wrongCount = questionResults.count { it.status == QuestionResultStatus.Incorrect }
    val skippedCount = questionResults.count { it.status == QuestionResultStatus.Skipped }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(R.string.results_journey_heading),
                style = ResultsSectionTitleTextStyle,
                color = QuizPrimaryText
            )
            Text(
                text = stringResource(R.string.results_journey_support),
                style = ResultsStatLabelTextStyle,
                color = QuizSecondaryText
            )
        }
        ResultsPanel(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    JourneySummaryPill(
                        label = stringResource(R.string.results_label_correct),
                        value = correctCount.toString(),
                        backgroundColor = QuizCorrectBackground,
                        valueColor = QuizCorrectAccent
                    )
                    JourneySummaryPill(
                        label = stringResource(R.string.results_label_wrong),
                        value = wrongCount.toString(),
                        backgroundColor = QuizIncorrectBackground,
                        valueColor = QuizIncorrectAccent
                    )
                    JourneySummaryPill(
                        label = stringResource(R.string.results_label_skipped),
                        value = skippedCount.toString(),
                        backgroundColor = QuizPrimarySoft,
                        valueColor = QuizPrimary
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    questionResults.forEachIndexed { index, result ->
                        JourneyTimelineRow(
                            result = result,
                            isLast = index == questionResults.lastIndex,
                            visible = index < visibleCount
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JourneySummaryPill(
    label: String,
    value: String,
    backgroundColor: Color,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = ResultsSectionTitleTextStyle,
            color = valueColor
        )
        Text(
            text = label,
            style = ResultsStatLabelTextStyle,
            color = QuizSecondaryText
        )
    }
}

@Composable
private fun JourneyTimelineRow(
    result: QuestionResultUiModel,
    isLast: Boolean,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    val statusLabel = result.status.labelText()
    val timelineDescription = stringResource(
        R.string.results_timeline_description,
        result.questionNumber,
        statusLabel
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "timelineRowAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.88f,
        animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
        label = "timelineRowScale"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
            }
            .padding(vertical = 4.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = timelineDescription
            },
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = result.questionNumber.toTimelineNumber(),
            style = ResultsTimelineNumberTextStyle,
            color = QuizSecondaryText,
            modifier = Modifier.width(28.dp)
        )
        HorizontalConnector(modifier = Modifier.padding(top = 9.dp))
        TimelineAxis(
            status = result.status,
            showConnector = !isLast,
            connectorVisible = visible
        )
        Text(
            text = statusLabel,
            style = ResultsTimelineStatusTextStyle,
            color = result.status.toLabelColor(),
            modifier = Modifier
                .padding(start = 12.dp, top = 1.dp)
                .weight(1f)
        )
    }
}

@Composable
private fun HorizontalConnector(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(22.dp)
            .height(2.dp)
            .background(QuizTimerTrack, RoundedCornerShape(percent = 50))
            .semantics(mergeDescendants = true) {}
    )
}

@Composable
private fun TimelineAxis(
    status: QuestionResultStatus,
    showConnector: Boolean,
    connectorVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimelineNode(status = status)
        if (showConnector) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(2.dp)
                    .height(28.dp)
                    .background(
                        color = QuizTimerTrack.copy(alpha = if (connectorVisible) 1f else 0f),
                        shape = RoundedCornerShape(percent = 50)
                    )
            )
        }
    }
}

@Composable
private fun TimelineNode(
    status: QuestionResultStatus,
    modifier: Modifier = Modifier
) {
    when (status) {
        QuestionResultStatus.Skipped -> Box(
            modifier = modifier
                .size(16.dp)
                .background(Color.Transparent, CircleShape)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = QuizTimerTrack,
                    radius = size.minDimension / 2f - 1.dp.toPx(),
                    style = Stroke(width = 1.75.dp.toPx())
                )
            }
        }

        else -> Surface(
            modifier = modifier.size(16.dp),
            shape = CircleShape,
            color = if (status == QuestionResultStatus.Correct) QuizCorrectAccent else QuizIncorrectAccent,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {}
    }
}

@Composable
private fun BackToSubjectsSection(
    onBackToSubjects: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onBackToSubjects,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .semantics { role = Role.Button },
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = QuizPrimary,
                contentColor = QuizOptionSurface
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.results_back_to_subjects),
                    style = ResultsButtonTextStyle
                )
            }
        }
        Text(
            text = stringResource(R.string.results_back_to_subjects_support),
            style = ResultsStatLabelTextStyle,
            color = QuizSecondaryText,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ResultsPanel(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(24.dp),
    backgroundColor: Color = QuizOptionSurface.copy(alpha = 0.94f),
    backgroundBrush: Brush? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = Color.Transparent,
        border = BorderStroke(1.dp, QuizOptionBorder.copy(alpha = 0.8f)),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier.background(
                brush = backgroundBrush ?: Brush.linearGradient(
                    colors = listOf(backgroundColor, backgroundColor)
                )
            )
        ) {
            content()
        }
    }
}

private fun Int.toTimelineNumber(): String = if (this < 100) "%02d".format(this) else toString()

@Composable
private fun QuestionResultStatus.labelText(): String = when (this) {
    QuestionResultStatus.Correct -> stringResource(R.string.results_label_correct)
    QuestionResultStatus.Incorrect -> stringResource(R.string.results_label_wrong)
    QuestionResultStatus.Skipped -> stringResource(R.string.results_label_skipped)
}

private fun QuestionResultStatus.toLabelColor(): Color = when (this) {
    QuestionResultStatus.Correct -> QuizCorrectAccent
    QuestionResultStatus.Incorrect -> QuizIncorrectAccent
    QuestionResultStatus.Skipped -> QuizSecondaryText
}

@Composable
private fun ResultsUiState.toInsights(): List<ResultsInsight> {
    val wrongAnswers = (totalQuestions - correctAnswers - skippedQuestions).coerceAtLeast(0)
    val milestoneCount = questionResults.count { it.completesStreakMilestone }

    return listOf(
        ResultsInsight(
            title = stringResource(R.string.results_insight_accuracy_title),
            value = when {
                accuracyPercent >= 90 -> stringResource(R.string.results_insight_accuracy_locked_in)
                accuracyPercent >= 75 -> stringResource(R.string.results_insight_accuracy_sharp_finish)
                accuracyPercent >= 60 -> stringResource(R.string.results_insight_accuracy_solid_base)
                else -> stringResource(R.string.results_insight_accuracy_room_to_grow)
            },
            supporting = stringResource(
                R.string.results_insight_accuracy_support,
                correctAnswers,
                wrongAnswers
            ),
            backgroundColor = QuizPrimarySoft,
            accentColor = QuizPrimary,
            icon = Icons.Filled.GpsFixed
        ),
        ResultsInsight(
            title = stringResource(R.string.results_insight_streak_title),
            value = if (longestStreak >= 3) {
                stringResource(R.string.results_insight_streak_heat_check, longestStreak)
            } else {
                stringResource(R.string.results_insight_streak_at, longestStreak)
            },
            supporting = if (milestoneCount > 0) {
                pluralStringResource(
                    R.plurals.results_insight_milestones_unlocked,
                    milestoneCount,
                    milestoneCount
                )
            } else {
                stringResource(R.string.results_insight_streak_none)
            },
            backgroundColor = QuizGoldWash,
            accentColor = QuizStreakActiveIcon,
            icon = Icons.Filled.LocalFireDepartment
        ),
        ResultsInsight(
            title = stringResource(R.string.results_insight_decision_title),
            value = if (skippedQuestions == 0) {
                stringResource(R.string.results_insight_decision_committed)
            } else {
                stringResource(R.string.results_insight_decision_measured)
            },
            supporting = if (skippedQuestions == 0) {
                stringResource(R.string.results_insight_decision_attempted_all)
            } else {
                pluralStringResource(
                    R.plurals.results_insight_decision_skipped_support,
                    skippedQuestions,
                    skippedQuestions
                )
            },
            backgroundColor = if (skippedQuestions == 0) QuizCorrectBackground else QuizIncorrectBackground,
            accentColor = if (skippedQuestions == 0) QuizCorrectAccent else QuizIncorrectAccent,
            icon = if (skippedQuestions == 0) Icons.Filled.TaskAlt else Icons.Filled.SkipNext
        )
    )
}

@Composable
private fun ResultsGlassOverlay(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            White.copy(alpha = 0.18f),
                            White.copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth(0.88f)
                .fillMaxHeight(0.52f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            White.copy(alpha = 0.24f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(0.46f)
                .fillMaxHeight(0.30f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            White.copy(alpha = 0.14f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

private data class ResultsInsight(
    val title: String,
    val value: String,
    val supporting: String,
    val backgroundColor: Color,
    val accentColor: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Preview(showBackground = true)
@Composable
private fun ResultsScreenPreview() {
    PreviewFrame {
        ResultsScreen(
            state = ResultsUiState(
                correctAnswers = 8,
                totalQuestions = 10,
                skippedQuestions = 1,
                longestStreak = 4,
                statusLabel = UiText.Resource(R.string.results_status_complete),
                headline = UiText.Resource(R.string.results_headline_great_work),
                supportingText = UiText.Resource(R.string.results_support_score, listOf(8, 10)),
                questionResults = listOf(
                    QuestionResultUiModel(1, QuestionResultStatus.Correct, false),
                    QuestionResultUiModel(2, QuestionResultStatus.Correct, false),
                    QuestionResultUiModel(3, QuestionResultStatus.Correct, true),
                    QuestionResultUiModel(4, QuestionResultStatus.Incorrect, false),
                    QuestionResultUiModel(5, QuestionResultStatus.Skipped, false),
                    QuestionResultUiModel(6, QuestionResultStatus.Correct, false),
                    QuestionResultUiModel(7, QuestionResultStatus.Correct, false),
                    QuestionResultUiModel(8, QuestionResultStatus.Correct, true),
                    QuestionResultUiModel(9, QuestionResultStatus.Correct, false),
                    QuestionResultUiModel(10, QuestionResultStatus.Correct, false)
                )
            ),
            onBackToSubjects = {}
        )
    }
}
