package com.example.quizapp.repository

import com.example.quizapp.network.RetrofitInstance

class QuizRepository {
    suspend fun getQuizData()=RetrofitInstance.api.getQuizData()
    suspend fun getCategoryWiseData(category:String)=RetrofitInstance.api.getCategoryWiseData(category = category)
    suspend fun getDifficultyWiseData(difficulty:String)=RetrofitInstance.api.getDifficultyWiseData(difficulty = difficulty)
}