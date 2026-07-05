package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.core.common.UiText
import com.example.quizassignment.feature.quiz.mapper.QuizUiMapper
import com.example.quizassignment.domain.usecase.AdvanceToNextQuestionUseCase
import com.example.quizassignment.domain.usecase.SelectAnswerUseCase
import com.example.quizassignment.domain.usecase.SkipQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val selectAnswerUseCase: SelectAnswerUseCase,
    private val skipQuestionUseCase: SkipQuestionUseCase,
    private val advanceToNextQuestionUseCase: AdvanceToNextQuestionUseCase,
    private val quizUiMapper: QuizUiMapper,
    private val quizSessionStore: QuizSessionStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()
    private var autoAdvanceJob: Job? = null

    init {
        viewModelScope.launch {
            combine(
                quizSessionStore.session,
                quizSessionStore.loadError
            ) { session, loadError -> session to loadError }
                .collect { (session, loadError) ->
                    _uiState.value = when {
                        session != null -> session.toUiState()
                        loadError != null -> QuizUiState(
                            supportingText = UiText.Resource(R.string.quiz_load_failed_support),
                            errorMessage = UiText.Dynamic(loadError)
                        )
                        else -> QuizUiState(
                            supportingText = UiText.Resource(R.string.quiz_session_unavailable)
                        )
                    }
                }
        }
    }

    fun onEvent(event: QuizEvent) {
        when (event) {
            is QuizEvent.AnswerSelected -> selectAnswer(event.optionIndex)
            QuizEvent.SkipClicked -> skipQuestion()
        }
    }

    override fun onCleared() {
        autoAdvanceJob?.cancel()
        super.onCleared()
    }

    private fun selectAnswer(optionIndex: Int) {
        val session = quizSessionStore.currentSession() ?: return
        if (session.currentRecord != null || session.isCompleted) return

        when (val result = selectAnswerUseCase(session, optionIndex)) {
            is AppResult.Error -> showError(result.message)
            is AppResult.Success -> {
                quizSessionStore.update(result.data)
                scheduleAutoAdvance()
            }
        }
    }

    private fun skipQuestion() {
        autoAdvanceJob?.cancel()
        val session = quizSessionStore.currentSession() ?: return
        if (session.currentRecord != null || session.isCompleted) return

        when (val skippedSession = skipQuestionUseCase(session)) {
            is AppResult.Error -> showError(skippedSession.message)
            is AppResult.Success -> advanceSession(skippedSession.data)
        }
    }

    private fun scheduleAutoAdvance() {
        autoAdvanceJob?.cancel()
        autoAdvanceJob = viewModelScope.launch {
            delay(QuizAutoAdvanceDelayMillis)
            quizSessionStore.currentSession()?.let(::advanceSession)
        }
    }

    private fun advanceSession(session: com.example.quizassignment.domain.model.QuizSession) {
        when (val result = advanceToNextQuestionUseCase(session)) {
            is AppResult.Error -> showError(result.message)
            is AppResult.Success -> quizSessionStore.update(result.data)
        }
    }

    private fun showError(message: String) {
        _uiState.value = _uiState.value.copy(errorMessage = UiText.Dynamic(message))
    }

    private fun com.example.quizassignment.domain.model.QuizSession.toUiState(): QuizUiState {
        if (isCompleted) {
            return _uiState.value.copy(
                canAnswer = false,
                canSkip = false,
                errorMessage = null,
                shouldNavigateToResults = true
            )
        }

        val question = currentQuestion ?: return QuizUiState(
            supportingText = UiText.Resource(R.string.quiz_session_unavailable)
        )
        val record = currentRecord
        val isAnswerRevealed = record?.wasSkipped == false

        return QuizUiState(
            currentQuestion = quizUiMapper.map(
                question = question,
                number = currentQuestionIndex + 1,
                total = questions.size
            ),
            currentStreak = currentStreak,
            longestStreak = longestStreak,
            selectedOptionIndex = record?.selectedOptionIndex,
            correctOptionIndex = if (isAnswerRevealed) question.correctOptionIndex else null,
            isAnswerRevealed = isAnswerRevealed,
            isStreakHighlighted = currentStreak >= 3,
            canAnswer = record == null,
            canSkip = record == null,
            isSessionReady = true,
            supportingText = when {
                record == null -> UiText.Resource(R.string.quiz_support_select_answer)
                record.wasCorrect -> UiText.Resource(R.string.quiz_support_correct_locked)
                else -> UiText.Resource(R.string.quiz_support_answer_revealed)
            },
            errorMessage = null,
            shouldNavigateToResults = false
        )
    }
}
