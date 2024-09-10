package com.example.retrofitlib

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object NetworkingLibrary {

    suspend fun <T> fetchSearchResults(
        query: String,
        includeAdult: Boolean = false,
        language: String = "en-US",
        page: Int = 1,
        baseUrl: String,
        token: String? = null,
        callback: (Result<ApiResponse<T>>) -> Unit
    ) {
        val apiService = NetworkModule.createService(ApiService::class.java, baseUrl, token)
        val call = apiService.search<T>(query, includeAdult, language, page)

        call.enqueue(object : Callback<ApiResponse<T>> {
            override fun onResponse(
                call: Call<ApiResponse<T>>,
                response: Response<ApiResponse<T>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Result.Success(it))
                    } ?: callback(Result.Error(NullPointerException("Response body is null")))
                } else {
                    callback(Result.Error(HttpException(response)))
                }
            }

            override fun onFailure(call: Call<ApiResponse<T>>, t: Throwable) {
                callback(Result.Error(t))
            }
        })
    }
}

