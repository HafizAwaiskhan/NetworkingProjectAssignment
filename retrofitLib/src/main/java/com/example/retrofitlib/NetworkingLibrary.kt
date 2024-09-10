package com.example.retrofitlib

object NetworkingLibrary {

    fun createApiService(baseUrl: String, token: String? = null): ApiService {
        return NetworkModule.createService(ApiService::class.java, baseUrl, token)
    }
}