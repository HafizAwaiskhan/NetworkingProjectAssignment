package com.example.retrofitlib

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseDeserializer<T> : JsonDeserializer<ApiResponse<T>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ApiResponse<T> {
        val jsonObject = json.asJsonObject

        val resultsType = TypeToken.getParameterized(List::class.java, (typeOfT as ParameterizedType).actualTypeArguments[0]).type
        val results = context.deserialize<T>(
            jsonObject.get("results"),
            resultsType
        ) ?: throw NullPointerException("Data is null")
        val status = jsonObject.get("status")?.asString ?: "unknown"
        val message = jsonObject.get("message")?.asString ?: "no message"

        return ApiResponse(results, status, message)
    }
}