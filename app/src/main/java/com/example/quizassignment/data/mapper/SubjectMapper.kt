package com.example.quizassignment.data.mapper

import com.example.quizassignment.data.datasource.remote.dto.SubjectDto
import com.example.quizassignment.domain.model.Subject
import javax.inject.Inject

class SubjectMapper @Inject constructor() {
    fun map(dto: SubjectDto): Subject = Subject(
        id = dto.id,
        title = dto.title,
        description = dto.description,
        questionsUrl = dto.questionsUrl
    )
}
