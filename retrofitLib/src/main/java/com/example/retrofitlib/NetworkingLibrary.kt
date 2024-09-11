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
    ):  ApiResponse<T> {
        val apiService = NetworkModule.createService(ApiService::class.java, baseUrl, token)
        return try {
            apiService.search(query, includeAdult, language, page)
        } catch (e: Exception) {
            throw e
        }
    }
}

