package com.example.quizassignment.domain.usecase

import com.example.quizassignment.core.common.AppResult
import com.example.quizassignment.core.common.DispatchersProvider
import com.example.quizassignment.domain.model.QuizProgress
import com.example.quizassignment.domain.repository.QuizRepository
import javax.inject.Inject
import kotlinx.coroutines.withContext

class GetQuizProgressUseCase @Inject constructor(
    private val quizRepository: QuizRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke(subjectId: String): AppResult<QuizProgress?> =
        withContext(dispatchersProvider.io) {
            quizRepository.getProgress(subjectId)
        }
}
