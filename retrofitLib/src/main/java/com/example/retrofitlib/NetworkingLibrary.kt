package com.example.retrofitlib

import retrofit2.HttpException

object NetworkingLibrary {

    suspend fun <T> fetchSearchResults(
        query: String,
        includeAdult: Boolean = false,
        language: String = "en-US",
        page: Int = 1,
        baseUrl: String,
        token: String? = null
    ): Result<ApiResponse<T>> {
        val apiService = NetworkModule.createService(ApiService::class.java, baseUrl, token)
        return try {
            val response = apiService.search<T>(query, includeAdult, language, page).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(NullPointerException("Response body is null"))
            } else {
                Result.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

