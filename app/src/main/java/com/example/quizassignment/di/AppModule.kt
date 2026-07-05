package com.example.quizassignment.di

import com.example.quizassignment.core.common.DefaultDispatchersProvider
import com.example.quizassignment.core.common.DispatchersProvider
import com.example.quizassignment.data.datasource.remote.QuizApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider = DefaultDispatchersProvider()

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideQuizApi(retrofit: Retrofit): QuizApi = retrofit.create(QuizApi::class.java)
}
