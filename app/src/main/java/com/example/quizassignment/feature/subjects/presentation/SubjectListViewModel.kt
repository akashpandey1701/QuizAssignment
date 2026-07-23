package com.example.quizassignment.feature.subjects.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.Subject
import com.example.quizassignment.domain.usecase.GetQuizProgressUseCase
import com.example.quizassignment.domain.usecase.LoadQuizQuestionsUseCase
import com.example.quizassignment.domain.usecase.LoadSubjectsUseCase
import com.example.quizassignment.domain.usecase.SaveQuizProgressUseCase
import com.example.quizassignment.domain.usecase.StartQuizSessionUseCase
import com.example.quizassignment.feature.quiz.presentation.QuizSessionStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SubjectListViewModel @Inject constructor(
    private val loadSubjectsUseCase: LoadSubjectsUseCase,
    private val loadQuizQuestionsUseCase: LoadQuizQuestionsUseCase,
    private val getQuizProgressUseCase: GetQuizProgressUseCase,
    private val saveQuizProgressUseCase: SaveQuizProgressUseCase,
    private val startQuizSessionUseCase: StartQuizSessionUseCase,
    private val quizSessionStore: QuizSessionStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubjectListUiState())
    val uiState: StateFlow<SubjectListUiState> = _uiState.asStateFlow()
    private var loadedSubjects: List<Subject> = emptyList()

    init {
        loadSubjects()
    }

    fun retry() {
        loadSubjects()
    }

    fun selectSubject(subjectId: String) {
        if (_uiState.value.openingSubjectId != null) return
        val subject = loadedSubjects.firstOrNull { it.id == subjectId } ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                openingSubjectId = subjectId,
                errorMessage = null
            )
            when (val progressResult = getQuizProgressUseCase(subjectId)) {
                is AppResult.Error -> showSelectionError(progressResult.message)
                is AppResult.Success -> {
                    val savedProgress = progressResult.data
                    if (savedProgress != null) {
                        openSavedQuiz(subject, savedProgress)
                    } else {
                        startNewQuiz(subject)
                    }
                }
            }
        }
    }

    fun consumeDestination() {
        _uiState.value = _uiState.value.copy(destination = null)
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            _uiState.value = SubjectListUiState()
            when (val result = loadSubjectsUseCase()) {
                is AppResult.Error -> {
                    _uiState.value = SubjectListUiState(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                is AppResult.Success -> {
                    loadedSubjects = result.data
                    val items = mutableListOf<SubjectItemUiModel>()
                    for (subject in loadedSubjects) {
                        when (val progress = getQuizProgressUseCase(subject.id)) {
                            is AppResult.Error -> {
                                _uiState.value = SubjectListUiState(
                                    isLoading = false,
                                    errorMessage = progress.message
                                )
                                return@launch
                            }

                            is AppResult.Success -> items += subject.toUiModel(progress.data)
                        }
                    }
                    _uiState.value = SubjectListUiState(
                        isLoading = false,
                        subjects = items
                    )
                }
            }
        }
    }

    private suspend fun startNewQuiz(subject: Subject) {
        when (val questionsResult = loadQuizQuestionsUseCase(subject.questionsUrl)) {
            is AppResult.Error -> showSelectionError(questionsResult.message)
            is AppResult.Success -> {
                when (val sessionResult = startQuizSessionUseCase(questionsResult.data)) {
                    is AppResult.Error -> showSelectionError(sessionResult.message)
                    is AppResult.Success -> {
                        val progress = QuizProgress(subject, sessionResult.data)
                        when (val saveResult = saveQuizProgressUseCase(progress)) {
                            is AppResult.Error -> showSelectionError(saveResult.message)
                            is AppResult.Success -> openQuiz(progress)
                        }
                    }
                }
            }
        }
    }

    private fun openSavedQuiz(
        currentSubject: Subject,
        savedProgress: QuizProgress
    ) {
        openQuiz(savedProgress.copy(subject = currentSubject))
    }

    private fun openQuiz(progress: QuizProgress) {
        quizSessionStore.open(progress.subject, progress.session)
        _uiState.value = _uiState.value.copy(
            subjects = _uiState.value.subjects.map { item ->
                if (item.id == progress.subject.id) {
                    item.copy(
                        quizState = if (progress.isCompleted) {
                            SubjectQuizState.Completed
                        } else {
                            SubjectQuizState.InProgress
                        }
                    )
                } else {
                    item
                }
            },
            openingSubjectId = null,
            destination = SubjectDestination(
                subjectId = progress.subject.id,
                screen = if (progress.isCompleted) {
                    SubjectDestinationScreen.Results
                } else {
                    SubjectDestinationScreen.Quiz
                }
            )
        )
    }

    private fun showSelectionError(message: String) {
        _uiState.value = _uiState.value.copy(
            openingSubjectId = null,
            errorMessage = message
        )
    }

    private fun Subject.toUiModel(progress: QuizProgress?): SubjectItemUiModel {
        val quizState = when {
            progress == null -> SubjectQuizState.NotStarted
            progress.isCompleted -> SubjectQuizState.Completed
            else -> SubjectQuizState.InProgress
        }
        return SubjectItemUiModel(
            id = id,
            title = title,
            description = description,
            quizState = quizState
        )
    }
}
