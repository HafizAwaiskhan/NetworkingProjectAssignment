package com.example.retrofitlib

data class ApiResponse<T>(
    val data: T,
    val status: String,
    val message: String
)
