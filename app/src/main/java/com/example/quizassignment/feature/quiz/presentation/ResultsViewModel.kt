package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.core.common.UiText
import com.example.quizassignment.domain.model.QuestionRecord
import com.example.quizassignment.domain.usecase.BuildQuizResultUseCase
import com.example.quizassignment.domain.usecase.RestartQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val buildQuizResultUseCase: BuildQuizResultUseCase,
    private val restartQuizUseCase: RestartQuizUseCase,
    private val quizSessionStore: QuizSessionStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultsUiState())
    val uiState: StateFlow<ResultsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quizSessionStore.session.collect { session ->
                if (session == null) {
                    _uiState.value = ResultsUiState()
                    return@collect
                }

                _uiState.value = when (val result = buildQuizResultUseCase(session)) {
                    is AppResult.Error -> ResultsUiState(
                        statusLabel = UiText.Resource(R.string.results_status_complete),
                        headline = UiText.Resource(R.string.results_headline_unavailable),
                        supportingText = UiText.Dynamic(result.message)
                    )

                    is AppResult.Success -> {
                        val summary = result.data
                        ResultsUiState(
                            correctAnswers = summary.correctAnswers,
                            totalQuestions = summary.totalQuestions,
                            skippedQuestions = summary.skippedQuestions,
                            longestStreak = summary.longestStreak,
                            statusLabel = UiText.Resource(R.string.results_status_complete),
                            headline = UiText.Resource(R.string.results_headline_great_work),
                            supportingText = UiText.Resource(
                                R.string.results_support_score,
                                listOf(
                                    summary.correctAnswers,
                                    summary.totalQuestions
                                )
                            ),
                            questionResults = session.records.toQuestionResults()
                        )
                    }
                }
            }
        }
    }

    fun restartQuiz(): Boolean {
        return when (val result = restartQuizUseCase(quizSessionStore.currentSession())) {
            is AppResult.Error -> false
            is AppResult.Success -> {
                quizSessionStore.update(result.data)
                true
            }
        }
    }

    private fun List<QuestionRecord>.toQuestionResults(): List<QuestionResultUiModel> {
        var currentStreak = 0
        return mapIndexed { index, record ->
            val status = when {
                record.wasSkipped -> QuestionResultStatus.Skipped
                record.wasCorrect -> QuestionResultStatus.Correct
                else -> QuestionResultStatus.Incorrect
            }
            currentStreak = when (status) {
                QuestionResultStatus.Correct -> currentStreak + 1
                QuestionResultStatus.Incorrect, QuestionResultStatus.Skipped -> 0
            }
            QuestionResultUiModel(
                questionNumber = index + 1,
                status = status,
                completesStreakMilestone = status == QuestionResultStatus.Correct && currentStreak == 3
            )
        }
    }
}
