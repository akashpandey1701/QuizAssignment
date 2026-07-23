package com.example.quizassignment.feature.quiz.presentation

import com.example.quizassignment.domain.model.QuizSession
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.model.Subject
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
class QuizSessionStore @Inject constructor() {

    private val _subject = MutableStateFlow<Subject?>(null)
    val subject: StateFlow<Subject?> = _subject.asStateFlow()
    private val _session = MutableStateFlow<QuizSession?>(null)
    val session: StateFlow<QuizSession?> = _session.asStateFlow()
    private val _loadError = MutableStateFlow<String?>(null)
    val loadError: StateFlow<String?> = _loadError.asStateFlow()

    fun currentSession(): QuizSession? = _session.value
    fun currentSubject(): Subject? = _subject.value

    fun currentProgress(): QuizProgress? {
        val subject = _subject.value ?: return null
        val session = _session.value ?: return null
        return QuizProgress(subject = subject, session = session)
    }

    fun open(subject: Subject, session: QuizSession) {
        _subject.value = subject
        update(session)
    }

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
        _subject.value = null
        _session.value = null
        _loadError.value = null
    }
}
