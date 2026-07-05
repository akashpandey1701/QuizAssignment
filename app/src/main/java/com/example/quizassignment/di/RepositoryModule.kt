package com.example.quizassignment.di

import com.example.quizassignment.data.repository.QuizRepositoryImpl
import com.example.quizassignment.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        implementation: QuizRepositoryImpl
    ): QuizRepository
}
