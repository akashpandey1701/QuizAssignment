package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.domain.model.QuizSession
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class QuizSessionStore @Inject constructor() {

    private val _session = MutableStateFlow<QuizSession?>(null)
    val session: StateFlow<QuizSession?> = _session.asStateFlow()
    private val _loadError = MutableStateFlow<String?>(null)
    val loadError: StateFlow<String?> = _loadError.asStateFlow()

    fun currentSession(): QuizSession? = _session.value

    fun update(session: QuizSession?) {
        _session.value = session
        if (session != null) {
            _loadError.value = null
        }
    }

    fun setLoadError(message: String?) {
        _loadError.value = message
    }

    fun clear() {
        _session.value = null
        _loadError.value = null
    }
}
