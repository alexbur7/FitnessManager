package ru.alexbur.fintess_manager.network.data.converter

import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.serialization.ContentConverter
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.core.readText
import io.ktor.utils.io.core.toByteArray
import io.ktor.utils.io.readRemaining
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import ru.alexbur.fintess_manager.network.data.mappers.ErrorMapper
import ru.alexbur.fintess_manager.network.data.models.ErrorModelResponse
import ru.alexbur.fintess_manager.network.data.models.getDataOrNull
import ru.alexbur.fintess_manager.network.data.models.getErrorOrNull
import ru.alexbur.fintess_manager.network.data.serializer.ApiResponseSerializer

internal class ApiConverter(private val json: Json) : ContentConverter {

    private val serializationConverter by lazy { KotlinxSerializationConverter(json) }
    private val errorMapper by lazy { ErrorMapper() }

    override suspend fun deserialize(charset: Charset, typeInfo: TypeInfo, content: ByteReadChannel): Any? {
        val text = content.readRemaining().readText(charset)
        val response = json.decodeFromString(ApiResponseSerializer, text)
        val data = response.getDataOrNull()?.toByteReadChannel()?.let {
            serializationConverter.deserialize(charset, typeInfo, it)
        }
        if (data == null) {
            throw errorMapper.map(
                json.decodeFromString(
                    ErrorModelResponse.serializer(),
                    response.getErrorOrNull().toString()
                )
            )
        }
        return data
    }

    override suspend fun serialize(
        contentType: ContentType,
        charset: Charset,
        typeInfo: TypeInfo,
        value: Any?
    ): OutgoingContent? {
        return serializationConverter.serialize(contentType, charset, typeInfo, value)
    }

    private fun JsonElement.toByteReadChannel(): ByteReadChannel {
        val jsonString = Json.encodeToString(JsonElement.serializer(), this)
        return ByteReadChannel(jsonString.toByteArray())
    }
}