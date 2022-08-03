package com.example.quizapp.network

import com.example.quizapp.model.QuizAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizAPI {

    @GET("api.php")
    suspend fun getQuizData(
        @Query("amount")
        amount: Int=10,
    ): Response<QuizAPIResponse>

    @GET("api.php")
    suspend fun getCategoryWiseData(
        @Query("amount")
        amount: Int=10,
        @Query("category")
        category: String,
    ): Response<QuizAPIResponse>

    @GET("api.php")
    suspend fun getDifficultyWiseData(
        @Query("amount")
        amount: Int=10,
        @Query("difficulty")
        difficulty:String,
    ): Response<QuizAPIResponse>
}