package com.example.retrofitlib

data class ApiResponse<T>(
    val results: T,
    val status: String,
    val message: String
)
