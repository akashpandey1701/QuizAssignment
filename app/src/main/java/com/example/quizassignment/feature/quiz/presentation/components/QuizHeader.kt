package com.example.quizassignment.feature.quiz.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizassignment.R
import com.example.quizassignment.core.designsystem.theme.QuizHeaderBadgeTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizHeaderProgressTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizPrimary
import com.example.quizassignment.core.designsystem.theme.QuizPrimaryText
import com.example.quizassignment.core.designsystem.theme.QuizSecondaryText
import com.example.quizassignment.core.designsystem.theme.QuizSkipTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizStreakActiveIcon
import com.example.quizassignment.core.designsystem.theme.QuizStreakInactiveIcon
import com.example.quizassignment.core.designsystem.theme.QuizTimerActive
import com.example.quizassignment.core.designsystem.theme.QuizTimerTrack
import com.example.quizassignment.feature.quiz.presentation.QuizAutoAdvanceDelayMillis

@Composable
fun QuizHeader(
    questionNumber: Int,
    totalQuestions: Int,
    currentStreak: Int,
    isStreakHighlighted: Boolean,
    canSkip: Boolean,
    onSkipClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HeaderTopRow(
            questionNumber = questionNumber,
            totalQuestions = totalQuestions,
            currentStreak = currentStreak,
            isStreakHighlighted = isStreakHighlighted,
            canSkip = canSkip,
            onSkipClicked = onSkipClicked
        )
        Spacer(modifier = Modifier.height(8.dp))
        QuestionProgress(
            progress = if (totalQuestions > 0) {
                questionNumber.toFloat() / totalQuestions.toFloat()
            } else {
                0f
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeaderTopRow(
    questionNumber: Int,
    totalQuestions: Int,
    currentStreak: Int,
    isStreakHighlighted: Boolean,
    canSkip: Boolean,
    onSkipClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 44.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuestionProgressLabel(
            questionNumber = questionNumber,
            totalQuestions = totalQuestions,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StreakBadge(
                currentStreak = currentStreak,
                isStreakHighlighted = isStreakHighlighted
            )
            SkipButton(
                canSkip = canSkip,
                onSkipClicked = onSkipClicked
            )
        }
    }
}

@Composable
private fun QuestionProgressLabel(
    questionNumber: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
) {
    val questionPrefix = stringResource(R.string.quiz_header_question_prefix)
    val questionSuffix = stringResource(R.string.quiz_header_progress_suffix, totalQuestions)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = questionPrefix.trimEnd(),
            style = QuizHeaderProgressTextStyle,
            color = QuizPrimaryText
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = QuizPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                ) {
                    append(questionNumber.toString())
                }
                withStyle(
                    SpanStyle(
                        color = QuizPrimaryText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                ) {
                    append(questionSuffix)
                }
            },
            style = QuizHeaderProgressTextStyle
        )
    }
}

@Composable
private fun QuestionProgress(
    progress: Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        progress = { progress.coerceIn(0f, 1f) },
        modifier = modifier.height(6.dp),
        color = QuizPrimary,
        trackColor = QuizTimerTrack,
        gapSize = 0.dp,
        drawStopIndicator = {}
    )
}

@Composable
private fun StreakBadge(
    currentStreak: Int,
    isStreakHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    val badgeScale = remember { Animatable(1f) }
    val activeDescription = stringResource(R.string.quiz_header_streak_active, currentStreak)
    val defaultDescription = stringResource(R.string.quiz_header_streak, currentStreak)

    LaunchedEffect(isStreakHighlighted) {
        if (isStreakHighlighted) {
            badgeScale.snapTo(1f)
            badgeScale.animateTo(1.08f, animationSpec = tween(durationMillis = 110))
            badgeScale.animateTo(1f, animationSpec = tween(durationMillis = 150))
        } else {
            badgeScale.snapTo(1f)
        }
    }

    Row(
        modifier = modifier
            .scale(badgeScale.value)
            .semantics {
                liveRegion = LiveRegionMode.Polite
                stateDescription = if (isStreakHighlighted) {
                    activeDescription
                } else {
                    defaultDescription
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.LocalFireDepartment,
                contentDescription = null,
                tint = if (isStreakHighlighted) QuizStreakActiveIcon else QuizStreakInactiveIcon,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = currentStreak.toString(),
                style = QuizHeaderBadgeTextStyle,
                color = QuizPrimaryText
            )
        }
    }
}

@Composable
private fun SkipButton(
    canSkip: Boolean,
    onSkipClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        modifier = modifier,
        enabled = canSkip,
        onClick = onSkipClicked
    ) {
        Text(
            text = stringResource(R.string.quiz_skip),
            style = QuizSkipTextStyle,
            color = if (canSkip) QuizPrimary else QuizSecondaryText.copy(alpha = 0.52f)
        )
    }
}

@Composable
fun AutoAdvanceProgress(
    isAnswerRevealed: Boolean,
    currentQuestionId: Int,
    modifier: Modifier = Modifier
) {
    if (!isAnswerRevealed) {
        return
    }

    val progress = remember(currentQuestionId, isAnswerRevealed) {
        Animatable(if (isAnswerRevealed) 1f else 0f)
    }

    LaunchedEffect(currentQuestionId, isAnswerRevealed) {
        if (isAnswerRevealed) {
            progress.snapTo(1f)
            progress.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = QuizAutoAdvanceDelayMillis.toInt(),
                    easing = LinearEasing
                )
            )
        } else {
            progress.snapTo(0f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(
                color = QuizTimerTrack,
                shape = RoundedCornerShape(percent = 50)
            )
    ) {
        if (progress.value > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.value)
                    .height(4.dp)
                    .background(
                        color = QuizTimerActive,
                        shape = RoundedCornerShape(percent = 50)
                    )
            )
        }
    }
}
