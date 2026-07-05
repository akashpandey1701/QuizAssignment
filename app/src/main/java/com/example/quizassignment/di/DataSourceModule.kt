package com.example.quizassignment.di

import com.example.quizassignment.data.datasource.remote.QuizRemoteDataSource
import com.example.quizassignment.data.datasource.remote.RetrofitQuizRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindQuizRemoteDataSource(
        implementation: RetrofitQuizRemoteDataSource
    ): QuizRemoteDataSource
}
