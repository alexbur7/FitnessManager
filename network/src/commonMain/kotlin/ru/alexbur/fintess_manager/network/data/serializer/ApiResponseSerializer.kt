package ru.alexbur.fintess_manager.network.data.serializer

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import ru.alexbur.fintess_manager.network.data.models.ApiResponse

internal object ApiResponseSerializer : JsonContentPolymorphicSerializer<ApiResponse>(ApiResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ApiResponse> {
        return when {
            "data" in element.jsonObject -> ApiResponse.Success.serializer()
            "error" in element.jsonObject -> ApiResponse.Error.serializer()
            else -> throw SerializationException("Unknown response format: $element")
        }
    }
}