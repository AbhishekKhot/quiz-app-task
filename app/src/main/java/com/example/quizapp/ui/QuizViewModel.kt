package com.example.quizapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.QuizAPIResponse
import com.example.quizapp.repository.QuizRepository
import com.example.quizapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class QuizViewModel(val repository: QuizRepository) : ViewModel() {
    val quizData: MutableLiveData<Resource<QuizAPIResponse>> = MutableLiveData()
    val categoryData: MutableLiveData<Resource<QuizAPIResponse>> = MutableLiveData()
    val difficultyData: MutableLiveData<Resource<QuizAPIResponse>> = MutableLiveData()

    init {
        getQuizData()
    }

    private fun getQuizData() = viewModelScope.launch {
        quizData.postValue(Resource.Loading())
        val response = repository.getQuizData()
        quizData.postValue(handleQuizDataResponse(response))
    }

    fun getCategoryWiseQuizData(category: String) = viewModelScope.launch {
        categoryData.postValue(Resource.Loading())
        val response = repository.getCategoryWiseData(category)
        categoryData.postValue(handleCategoryQuizDataResponse(response))
    }

    fun getDifficultyWiseQuizData(difficulty: String) = viewModelScope.launch {
        difficultyData.postValue(Resource.Loading())
        val response = repository.getDifficultyWiseData(difficulty)
        difficultyData.postValue(handleDifficultyQuizDataResponse(response))
    }

    private fun handleDifficultyQuizDataResponse(response: Response<QuizAPIResponse>): Resource<QuizAPIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleCategoryQuizDataResponse(response: Response<QuizAPIResponse>): Resource<QuizAPIResponse>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleQuizDataResponse(response: Response<QuizAPIResponse>): Resource<QuizAPIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}