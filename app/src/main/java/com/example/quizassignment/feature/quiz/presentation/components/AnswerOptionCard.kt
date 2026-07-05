package com.example.quizassignment.feature.quiz.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.quizassignment.R
import com.example.quizassignment.core.designsystem.theme.QuizAnswerTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizCorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizCorrectBackground
import com.example.quizassignment.core.designsystem.theme.QuizIncorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizIncorrectBackground
import com.example.quizassignment.core.designsystem.theme.QuizOptionBorder
import com.example.quizassignment.core.designsystem.theme.QuizOptionLabelTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizOptionSurface
import com.example.quizassignment.core.designsystem.theme.QuizPrimary
import com.example.quizassignment.core.designsystem.theme.QuizPrimarySoft
import com.example.quizassignment.core.designsystem.theme.QuizPrimaryText
import com.example.quizassignment.core.designsystem.theme.QuizSecondaryText

@Composable
fun AnswerOptionCard(
    label: String,
    optionLabel: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isAnswerRevealed: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val isWrongSelection = isAnswerRevealed && isSelected && !isCorrect
    val isCorrectReveal = isAnswerRevealed && isCorrect
    val isResolved = isCorrectReveal || isWrongSelection
    val containerColor by animateColorAsState(
        targetValue = when {
            isCorrectReveal -> QuizCorrectBackground
            isWrongSelection -> QuizIncorrectBackground
            isSelected -> QuizPrimarySoft
            else -> QuizOptionSurface
        },
        label = "answerRowContainer"
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            isCorrectReveal -> QuizCorrectAccent.copy(alpha = 0.72f)
            isWrongSelection -> QuizIncorrectAccent.copy(alpha = 0.72f)
            isSelected -> QuizPrimary
            else -> QuizOptionBorder
        },
        label = "answerRowBorder"
    )
    val markerColor by animateColorAsState(
        targetValue = when {
            isCorrectReveal -> QuizCorrectAccent
            isWrongSelection -> QuizIncorrectAccent
            isSelected -> QuizPrimary
            else -> QuizSecondaryText.copy(alpha = 0.72f)
        },
        label = "answerMarker"
    )
    val markerContainerColor by animateColorAsState(
        targetValue = when {
            isCorrectReveal -> QuizCorrectBackground.copy(alpha = 0.96f)
            isWrongSelection -> QuizIncorrectBackground.copy(alpha = 0.96f)
            isSelected -> QuizPrimarySoft.copy(alpha = 0.9f)
            else -> QuizOptionSurface
        },
        label = "answerMarkerContainer"
    )
    val markerBorderColor by animateColorAsState(
        targetValue = when {
            isCorrectReveal -> QuizCorrectAccent.copy(alpha = 0.26f)
            isWrongSelection -> QuizIncorrectAccent.copy(alpha = 0.26f)
            isSelected -> QuizPrimary.copy(alpha = 0.32f)
            else -> QuizOptionBorder
        },
        label = "answerMarkerBorder"
    )
    val scale by animateFloatAsState(
        targetValue = if (isResolved) 1.012f else 1f,
        label = "answerRowScale"
    )
    val stateDescription = when {
        isCorrectReveal -> stringResource(R.string.quiz_answer_state_correct)
        isWrongSelection -> stringResource(R.string.quiz_answer_state_selected_incorrect)
        isSelected -> stringResource(R.string.quiz_answer_state_selected)
        else -> stringResource(R.string.quiz_answer_state_default)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .semantics { this.stateDescription = stateDescription }
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        shape = MaterialTheme.shapes.large,
        color = containerColor,
        contentColor = QuizPrimaryText,
        border = BorderStroke(1.dp, borderColor),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 76.dp)
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(14.dp),
                color = markerContainerColor,
                border = BorderStroke(1.dp, markerBorderColor),
                contentColor = when {
                    isCorrectReveal -> QuizCorrectAccent
                    isWrongSelection -> QuizIncorrectAccent
                    isSelected -> QuizPrimary
                    else -> QuizPrimaryText.copy(alpha = 0.72f)
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = optionLabel,
                        style = QuizOptionLabelTextStyle
                    )
                }
            }
            Text(
                text = label,
                style = QuizAnswerTextStyle,
                color = QuizPrimaryText,
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(
                visible = isAnswerRevealed && (isSelected || isCorrect),
                enter = fadeIn() + scaleIn(initialScale = 0.82f)
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = if (isCorrect) {
                        stringResource(R.string.quiz_answer_correct)
                    } else {
                        stringResource(R.string.quiz_answer_incorrect)
                    },
                    modifier = Modifier.size(22.dp),
                    tint = if (isCorrect) QuizCorrectAccent else QuizIncorrectAccent
                )
            }
        }
    }
}
