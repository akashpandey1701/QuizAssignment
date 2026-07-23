package com.example.quizassignment.feature.subjects.presentation

data class SubjectListUiState(
    val isLoading: Boolean = true,
    val subjects: List<SubjectItemUiModel> = emptyList(),
    val openingSubjectId: String? = null,
    val errorMessage: String? = null,
    val destination: SubjectDestination? = null
)

data class SubjectItemUiModel(
    val id: String,
    val title: String,
    val description: String,
    val quizState: SubjectQuizState
)

enum class SubjectQuizState {
    NotStarted,
    InProgress,
    Completed
}

data class SubjectDestination(
    val subjectId: String,
    val screen: SubjectDestinationScreen
)

enum class SubjectDestinationScreen {
    Quiz,
    Results
}
