package com.example.retrofitlib

import com.google.gson.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseDeserializer<T> : JsonDeserializer<ApiResponse<T>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ApiResponse<T> {
        val jsonObject = json.asJsonObject

        val status = jsonObject.get("status")?.asString ?: "unknown"
        val message = jsonObject.get("message")?.asString ?: "no message"
        val data = context.deserialize<T>(
            jsonObject.get("data"),
            (typeOfT as ParameterizedType).actualTypeArguments[0]
        ) ?: throw NullPointerException("Data is null")

        return ApiResponse(data, status, message)
    }
}