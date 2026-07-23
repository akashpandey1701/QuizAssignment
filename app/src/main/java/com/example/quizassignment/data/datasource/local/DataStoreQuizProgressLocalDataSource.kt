package com.example.quizassignment.data.datasource.local

import androidx.datastore.core.DataStore
import com.example.quizassignment.data.datasource.local.model.QuizProgressStore
import com.example.quizassignment.data.mapper.QuizProgressMapper
import com.example.quizassignment.domain.model.QuizProgress
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class DataStoreQuizProgressLocalDataSource @Inject constructor(
    private val dataStore: DataStore<QuizProgressStore>,
    private val mapper: QuizProgressMapper
) : QuizProgressLocalDataSource {

    override suspend fun getProgress(subjectId: String): QuizProgress? {
        return dataStore.data.first()
            .progressBySubjectId[subjectId]
            ?.let(mapper::toDomain)
    }

    override suspend fun saveProgress(progress: QuizProgress) {
        dataStore.updateData { current ->
            current.copy(
                progressBySubjectId = current.progressBySubjectId +
                    (progress.subject.id to mapper.toPersisted(progress))
            )
        }
    }
}
