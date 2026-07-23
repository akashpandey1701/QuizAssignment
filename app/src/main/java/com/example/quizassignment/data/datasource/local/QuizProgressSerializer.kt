package com.example.quizassignment.data.datasource.local

import androidx.datastore.core.Serializer
import com.example.quizassignment.data.datasource.local.model.QuizProgressStore
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class QuizProgressSerializer(
    private val json: Json
) : Serializer<QuizProgressStore> {

    override val defaultValue: QuizProgressStore = QuizProgressStore()

    override suspend fun readFrom(input: InputStream): QuizProgressStore {
        return try {
            json.decodeFromString(input.readBytes().decodeToString())
        } catch (_: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(
        t: QuizProgressStore,
        output: OutputStream
    ) {
        output.write(json.encodeToString(t).encodeToByteArray())
    }
}
