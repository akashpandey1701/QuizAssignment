package com.example.quizassignment.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quizassignment.core.designsystem.theme.QuizAssignmentTheme

@Composable
fun PreviewFrame(content: @Composable () -> Unit) {
    QuizAssignmentTheme {
        androidx.compose.material3.Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}
