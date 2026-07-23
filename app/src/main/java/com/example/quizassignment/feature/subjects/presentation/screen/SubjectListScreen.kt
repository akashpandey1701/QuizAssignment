package com.example.quizassignment.feature.subjects.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizassignment.R
import com.example.quizassignment.core.designsystem.components.AppEdgeToEdgeScaffold
import com.example.quizassignment.core.designsystem.theme.QuizCorrectAccent
import com.example.quizassignment.core.designsystem.theme.QuizCorrectBackground
import com.example.quizassignment.core.designsystem.theme.QuizGold
import com.example.quizassignment.core.designsystem.theme.QuizGoldWash
import com.example.quizassignment.core.designsystem.theme.QuizHeaderBackground
import com.example.quizassignment.core.designsystem.theme.QuizOptionBorder
import com.example.quizassignment.core.designsystem.theme.QuizOptionSurface
import com.example.quizassignment.core.designsystem.theme.QuizPrimary
import com.example.quizassignment.core.designsystem.theme.QuizPrimarySoft
import com.example.quizassignment.core.designsystem.theme.QuizPrimaryText
import com.example.quizassignment.core.designsystem.theme.QuizSecondaryText
import com.example.quizassignment.feature.quiz.presentation.LoadingUiState
import com.example.quizassignment.feature.quiz.presentation.screen.LoadingScreen
import com.example.quizassignment.feature.subjects.presentation.SubjectDestinationScreen
import com.example.quizassignment.feature.subjects.presentation.SubjectItemUiModel
import com.example.quizassignment.feature.subjects.presentation.SubjectListUiState
import com.example.quizassignment.feature.subjects.presentation.SubjectListViewModel
import com.example.quizassignment.feature.subjects.presentation.SubjectQuizState

@Composable
fun SubjectListRoute(
    onOpenQuiz: (String) -> Unit,
    onOpenResults: (String) -> Unit,
    viewModel: SubjectListViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(state.destination) {
        val destination = state.destination ?: return@LaunchedEffect
        when (destination.screen) {
            SubjectDestinationScreen.Quiz -> onOpenQuiz(destination.subjectId)
            SubjectDestinationScreen.Results -> onOpenResults(destination.subjectId)
        }
        viewModel.consumeDestination()
    }

    SubjectListScreen(
        state = state,
        onSubjectClick = viewModel::selectSubject,
        onRetry = viewModel::retry
    )
}

@Composable
fun SubjectListScreen(
    state: SubjectListUiState,
    onSubjectClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        LoadingScreen(
            state = LoadingUiState(isLoading = true),
            modifier = modifier
        )
        return
    }

    AppEdgeToEdgeScaffold(
        modifier = modifier.background(QuizHeaderBackground)
    ) { innerPadding ->
        if (state.subjects.isEmpty()) {
            SubjectListError(
                message = state.errorMessage
                    ?: stringResource(R.string.subjects_empty),
                onRetry = onRetry,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
            return@AppEdgeToEdgeScaffold
        }

        SubjectListContent(
            state = state,
            onSubjectClick = onSubjectClick,
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun SubjectListContent(
    state: SubjectListUiState,
    onSubjectClick: (String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val horizontalPadding = if (maxWidth >= 600.dp) 32.dp else 20.dp

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .align(Alignment.TopCenter),
            contentPadding = PaddingValues(
                start = horizontalPadding,
                top = contentPadding.calculateTopPadding() + 28.dp,
                end = horizontalPadding,
                bottom = contentPadding.calculateBottomPadding() + 28.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SubjectHeader()
            }

            state.errorMessage?.let { message ->
                item {
                    InlineError(message)
                }
            }

            items(
                items = state.subjects,
                key = SubjectItemUiModel::id
            ) { subject ->
                SubjectCard(
                    subject = subject,
                    isOpening = state.openingSubjectId == subject.id,
                    enabled = state.openingSubjectId == null,
                    onClick = { onSubjectClick(subject.id) }
                )
            }
        }
    }
}

@Composable
private fun SubjectHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text(
            text = stringResource(R.string.subjects_eyebrow),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = QuizPrimary
        )
        Text(
            text = stringResource(R.string.subjects_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = QuizPrimaryText,
            modifier = Modifier.semantics { heading() }
        )
        Text(
            text = stringResource(R.string.subjects_support),
            style = MaterialTheme.typography.bodyLarge,
            color = QuizSecondaryText
        )
    }
}

@Composable
private fun SubjectCard(
    subject: SubjectItemUiModel,
    isOpening: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val status = subject.quizState.statusContent()
    val statusLabel = stringResource(status.label)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 118.dp)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .semantics {
                role = Role.Button
                stateDescription = statusLabel
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = QuizOptionSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(1.dp, QuizOptionBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(status.softColor, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = status.icon,
                    contentDescription = null,
                    tint = status.accentColor,
                    modifier = Modifier.size(25.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = subject.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = QuizPrimaryText
                )
                if (subject.description.isNotBlank()) {
                    Text(
                        text = subject.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = QuizSecondaryText,
                        maxLines = 2
                    )
                }
                Text(
                    text = statusLabel,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = status.accentColor
                )
            }

            if (isOpening) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.5.dp,
                    color = status.accentColor
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = status.accentColor
                )
            }
        }
    }
}

@Composable
private fun InlineError(
    message: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.errorContainer,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
private fun SubjectListError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.ErrorOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(42.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.subjects_error_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = QuizPrimaryText
        )
        Text(
            text = message,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = QuizSecondaryText
        )
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(stringResource(R.string.common_retry))
        }
    }
}

private data class SubjectStatusContent(
    val label: Int,
    val icon: ImageVector,
    val accentColor: Color,
    val softColor: Color
)

private fun SubjectQuizState.statusContent(): SubjectStatusContent = when (this) {
    SubjectQuizState.NotStarted -> SubjectStatusContent(
        label = R.string.subject_status_start,
        icon = Icons.Filled.PlayArrow,
        accentColor = QuizPrimary,
        softColor = QuizPrimarySoft
    )

    SubjectQuizState.InProgress -> SubjectStatusContent(
        label = R.string.subject_status_resume,
        icon = Icons.Filled.Replay,
        accentColor = QuizGold,
        softColor = QuizGoldWash
    )

    SubjectQuizState.Completed -> SubjectStatusContent(
        label = R.string.subject_status_completed,
        icon = Icons.Filled.CheckCircle,
        accentColor = QuizCorrectAccent,
        softColor = QuizCorrectBackground
    )
}
