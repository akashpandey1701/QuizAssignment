package com.example.quizassignment.feature.quiz.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizassignment.R
import com.example.quizassignment.core.designsystem.components.PreviewFrame
import com.example.quizassignment.core.designsystem.theme.QuizHeaderBackground
import com.example.quizassignment.core.designsystem.theme.QuizIncorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizPrimary
import com.example.quizassignment.core.designsystem.theme.QuizPrimarySoft
import com.example.quizassignment.core.designsystem.theme.QuizPrimaryText
import com.example.quizassignment.core.designsystem.theme.QuizQuestionTextStyle
import com.example.quizassignment.core.designsystem.theme.QuizSecondaryText
import com.example.quizassignment.core.designsystem.theme.QuizSupportingTextStyle
import com.example.quizassignment.core.designsystem.theme.White

@Composable
fun QuizLoadFailedScreen(
    onRetryLoad: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        QuizPrimarySoft,
                        QuizHeaderBackground,
                        White
                    )
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FailureSignal()

            Text(
                text = androidx.compose.ui.res.stringResource(R.string.quiz_load_failed_title),
                style = QuizQuestionTextStyle,
                color = QuizPrimaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 28.dp)
            )
            Text(
                text = androidx.compose.ui.res.stringResource(R.string.quiz_load_failed_support),
                style = QuizSupportingTextStyle,
                color = QuizSecondaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Button(
            onClick = onRetryLoad,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .heightIn(min = 54.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = QuizPrimary,
                contentColor = White
            )
        ) {
            Text(text = androidx.compose.ui.res.stringResource(R.string.common_retry))
        }
    }
}

@Composable
private fun FailureSignal() {
    Box(
        modifier = Modifier.size(188.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(White.copy(alpha = 0.42f))
        )
        Text(
            text = "!",
            style = QuizQuestionTextStyle.copy(fontSize = 64.sp),
            color = QuizIncorrectAccent
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizLoadFailedScreenPreview() {
    PreviewFrame {
        QuizLoadFailedScreen(
            onRetryLoad = {}
        )
    }
}
