package com.example.quizassignment.feature.quiz.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.usecase.LoadQuizQuestionsUseCase
import com.example.quizassignment.domain.usecase.StartQuizSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadQuizQuestionsUseCase: LoadQuizQuestionsUseCase,
    private val startQuizSessionUseCase: StartQuizSessionUseCase,
    private val quizSessionStore: QuizSessionStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoadingUiState())
    val uiState: StateFlow<LoadingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            prepareQuiz()
        }
    }

    fun retry() {
        viewModelScope.launch {
            prepareQuiz(forceReload = true)
        }
    }

    private suspend fun prepareQuiz(forceReload: Boolean = false) {
        _uiState.value = LoadingUiState()

        val existingSession = quizSessionStore.currentSession()
        if (!forceReload && existingSession != null && !existingSession.isCompleted) {
            _uiState.value = LoadingUiState(
                isLoading = false,
                shouldNavigateToQuiz = true
            )
            return
        }

        quizSessionStore.clear()

        val sessionResult = when (val loadResult = loadQuizQuestionsUseCase()) {
            is AppResult.Error -> {
                quizSessionStore.setLoadError(loadResult.message)
                _uiState.value = LoadingUiState(
                    isLoading = false,
                    shouldNavigateToQuiz = true
                )
                return
            }

            is AppResult.Success -> startQuizSessionUseCase(loadResult.data)
        }

        when (sessionResult) {
            is AppResult.Error -> {
                quizSessionStore.setLoadError(sessionResult.message)
                _uiState.value = LoadingUiState(
                    isLoading = false,
                    shouldNavigateToQuiz = true
                )
            }

            is AppResult.Success -> {
                quizSessionStore.update(sessionResult.data)
                _uiState.value = LoadingUiState(
                    isLoading = false,
                    shouldNavigateToQuiz = true
                )
            }
        }
    }
}
