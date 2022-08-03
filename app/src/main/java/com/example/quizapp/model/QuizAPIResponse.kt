package com.example.quizapp.model

data class QuizAPIResponse(
    val response_code: Int,
    val results: List<Result>
)